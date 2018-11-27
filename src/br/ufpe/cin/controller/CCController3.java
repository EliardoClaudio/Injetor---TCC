package br.ufpe.cin.controller;

import java.io.IOException;
import java.util.Date;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.enums.StateMachineEnum;
import br.ufpe.cin.support.MySshConnector;
import br.ufpe.cin.support.MyTimer;
import br.ufpe.cin.support.WriteFile;
import br.ufpe.cin.testes.temp.TestVMStateMachine;

/**
 * This class implements a CC3 controller
 * 
 * @author Eliardo Cl�udio - ecf@cin.ufpe.br
 *
 */
public class CCController3 {

    private StateMachineEnum state;
    private MyTimer timer;
    private MySshConnector sshConnection;
    private RandomVariateGenerator randF;
    private RandomVariateGenerator randR;

    public CCController3(MySshConnector sshConnection, RandomVariateGenerator failure, RandomVariateGenerator repair) {
	this.state = StateMachineEnum.RUNNING;
	this.setSshConnection(sshConnection);
	this.randF = failure;
	this.randR = repair;
    }

    public StateMachineEnum getState() {
	return state;
    }

    public void setState(StateMachineEnum state) {
	this.state = state;
    }

    public MyTimer getTimer() {
	return timer;
    }

    public void setTimer(MyTimer timer) {
	this.timer = timer;
    }

    public MySshConnector getSshConnection() {
	return sshConnection;
    }

    public void setSshConnection(MySshConnector sshConnection) {
	this.sshConnection = sshConnection;
    }

    public RandomVariateGenerator getRandF() {
	return randF;
    }

    public void setRandF(RandomVariateGenerator randF) {
	this.randF = randF;
    }

    public RandomVariateGenerator getRandR() {
	return randR;
    }

    public void setRandR(RandomVariateGenerator randR) {
	this.randR = randR;
    }

    /**
     * Generates a Random Number (double) according to the distribution
     * provided.
     * 
     * @return the truncated number to be used as a random failure time.
     */
    public int generateRandomFailureTime() {
	double b = this.randF.generateRandomNumber();
	int c = (int) b;
	return c;
    }

    /**
     * Generates a Random Number (double) according to the distribution
     * provided.
     * 
     * @return the truncated number to be used as a random repair time.
     */
    public int generateRandomRepairTime() {
	double b = this.randR.generateRandomNumber();
	int c = (int) b;
	return c;
    }

    /**
     * Checks if the Node Controller is dead or alive
     * 
     * @return true if the NC is running, otherwise returns false
     */
    public boolean isAlive() {
	this.getSshConnection().setCommand("service eucalyptus-nc status | awk '{print $3}'");
	String aux = this.getSshConnection().sshCommand().trim();
	System.out.println("entrou no metodo isAlive do CC3 e retornou: " + aux);
	if (aux.equals("running")) {
	    return true;
	} else
	    return false;
    }

    /**
     * Shuts-down the CC3
     */
    public void stopCC3() {
	//FIXME - REIMPLEMENTAR vendo a dependencia!!
	this.getSshConnection().setCommand("service eucalyptus-nc stop");
	this.getSshConnection().sshCommand();
    }

    /**
     * Starts the CC3
     * 
     */
    public void startCC3() {
	//FIXME - REIMPLEMENTAR vendo a dependencia!!
	this.getSshConnection().setCommand("service eucalyptus-nc start");
	this.getSshConnection().sshCommand();
    }

    /**
     * State Machine
     * 
     * @throws InterruptedException
     * @throws IOException 
     */
    public void runCCStateMachine3() throws InterruptedException, IOException {
	switch (this.getState()) {
	case RUNNING:
	    if (this.isAlive()) {
	    	int waitingTime = this.generateRandomFailureTime();
			this.setTimer(new MyTimer(waitingTime));
	    	WriteFile.logger("Started. Node3: " + this.getSshConnection().getHost(), "Node_log.txt");
	    	WriteFile.logger(new Date().toString(), "Node_log.txt");
			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
		    } else {
			// Sleep again until the CC starts
		    }
	    String status = this.getState().toString();
	    System.out.println(status);
	    while(!this.getTimer().isExpired()){
		 	WriteFile.logger("Up - "+ new Date().toString(), "Node_log.txt");
		 	Thread.sleep(6000);
		 	}
	    	break;
	    
	case TIMER_INJECT_FAILURE:
	    if (this.getTimer().isExpired()) {
			this.stopCC3();
			int waitingTime = this.generateRandomFailureTime();
			this.setTimer(new MyTimer(waitingTime));
			WriteFile.logger("Inject Fault. Node3: " + this.getSshConnection().getHost(), "Node_log.txt");
			WriteFile.logger(new Date().toString(), "Node_log.txt");
   			Thread.sleep(30000);
			this.setState(StateMachineEnum.FAILED);
		    } else {
			// Sleep again until the timer expires
		    }
	    	break;

	case FAILED:
	    if (!this.isAlive()) {
			int waitingTime = this.generateRandomFailureTime();
			this.setTimer(new MyTimer(waitingTime));
			this.setState(StateMachineEnum.TIMER_REPAIR);
			WriteFile.logger("Failed. Node3: " + this.getSshConnection().getHost(), "Node_log.txt");
	    	WriteFile.logger(new Date().toString(), "Node_log.txt");
		    } else {
			// // Sleep again until the CC stops
		    }
	    String statusDown = this.getState().toString();
	    System.out.println(statusDown);
	    while(!this.getTimer().isExpired()){
	    	WriteFile.logger("Down - "+ new Date().toString(), "Node_log.txt");
		 	Thread.sleep(8000);
		 	}
	    	break;

	case TIMER_REPAIR:
	    if (this.getTimer().isExpired()) {
			this.startCC3();
			int waitingTimeR = this.generateRandomRepairTime();
			this.setTimer(new MyTimer(waitingTimeR));
			WriteFile.logger("Repaired. Node3: " + this.getSshConnection().getHost(), "Node_log.txt");
			WriteFile.logger(new Date().toString(), "Node_log.txt");
   			Thread.sleep(30000);
			//this.setState(StateMachineEnum.RUNNING);
			
			TestVMStateMachine.Chamada("192.168.0.156", "clouds");
			System.exit(0);
		    } else {
			// Wait for the timer to expire
		    }
		    break;
		    
	}
  }
}