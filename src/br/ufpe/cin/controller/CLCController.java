package br.ufpe.cin.controller;

import java.io.IOException;
import java.util.Date;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.enums.StateMachineEnum;
import br.ufpe.cin.support.MySshConnector;
import br.ufpe.cin.support.MyTimer;
import br.ufpe.cin.support.WriteFile;
import br.ufpe.cin.testes.temp.TestVMStateMachine;

public class CLCController {

    private StateMachineEnum state;
    private MyTimer timer;
    private MySshConnector sshConnection;
    private RandomVariateGenerator randF;
    private RandomVariateGenerator randR;

    public CLCController(MySshConnector sshConnection, RandomVariateGenerator failure, RandomVariateGenerator repair) {
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
     * Checks if the Cloud Controller is dead or alive
     * 
     * @return true if the CLC is running, otherwise returns false
     */
    public boolean isAlive() {
	this.getSshConnection().setCommand(
		"curl -v --silent http://192.168.0.151:8773/services/Heartbeat 2>&1 | grep eucalyptus | awk '{print $2}'");
	String aux = this.getSshConnection().sshCommand();
	if (aux != null) {
	    aux = aux.trim();
	} else {
	    return false;
	}
	if (aux.equals("enabled=true")) {
		WriteFile.logger("Status: Up" , "Node_log.txt");
	    return true;
	} else
		WriteFile.logger("Status: Down" , "Node_log.txt");
	    return false;
    }

    /**
     * Shuts-down the CLC
     */
    public void stopCLC() {
	//FIXME - REIMPLEMENTAR considerando a dependencia com o hardware!
	this.getSshConnection().setCommand("service eucalyptus-cloud stop");
	this.getSshConnection().sshCommand();
    }

    /**
     * Starts the CLC
     * 
     */
    public void startCLC() {
	//FIXME - REIMPLEMENTAR considerando a dependencia com o hardware!
	this.getSshConnection().setCommand("service eucalyptus-cloud start");
	this.getSshConnection().sshCommand();
    }

    /**
     * State Machine
     * 
     * @throws InterruptedException
     * @throws IOException 
     */
    public void runCLCStateMachine() throws InterruptedException, IOException {
    	WriteFile.logger("Front: Conexão realizada com sucesso!", "Node_log.txt");
		switch (this.getState()) {
		case RUNNING:
		    if (this.isAlive()) {
		    	int waitingTime = this.generateRandomFailureTime();
				this.setTimer(new MyTimer(waitingTime));
		    	WriteFile.logger("Started. Front: " + this.getSshConnection().getHost(), "Node_log.txt");
		    	WriteFile.logger(new Date().toString(), "Node_log.txt");
				this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
			    } else {
				// Sleep again until the CC starts
			    }
		    while(!this.getTimer().isExpired()){
		    	WriteFile.logger("Up - "+ new Date().toString(), "Node_log.txt");
			 	Thread.sleep(6000);
			 	}
		    	break;
		    
		case TIMER_INJECT_FAILURE:
		    if (this.getTimer().isExpired()) {
				this.stopCLC();
				int waitingTime = this.generateRandomFailureTime();
				this.setTimer(new MyTimer(waitingTime));
				WriteFile.logger("Inject Fault. Front: " + this.getSshConnection().getHost(), "Node_log.txt");
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
				WriteFile.logger("Failed. Front: " + this.getSshConnection().getHost(), "Node_log.txt");
		    	WriteFile.logger(new Date().toString(), "Node_log.txt");
			    } else {
				// // Sleep again until the CC stops
			    }
		    while(!this.getTimer().isExpired()){
		    	WriteFile.logger("Down - "+ new Date().toString(), "Node_log.txt");
			 	Thread.sleep(8000);
			 	}
		    	break;
	
		case TIMER_REPAIR:
		    if (this.getTimer().isExpired()) {
				this.startCLC();
				int waitingTimeR = this.generateRandomRepairTime();
				this.setTimer(new MyTimer(waitingTimeR));
				WriteFile.logger("Repaired. Front: " + this.getSshConnection().getHost(), "Node_log.txt");
				WriteFile.logger(new Date().toString(), "Node_log.txt");
	   			Thread.sleep(30000);
				this.setState(StateMachineEnum.RUNNING);
				
				//Chamando novo VM1...
	   			TestVMStateMachine.Chamada("192.168.0.152", "clouds");
	   			System.exit(0);
			    } else {
				// Wait for the timer to expire
			    }
			    break;
			    
		}
	  }
	}
