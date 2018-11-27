package br.ufpe.cin.controller;

import java.util.Date;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.enums.StateMachineEnum;
import br.ufpe.cin.support.MySshConnector;
import br.ufpe.cin.support.MyTimer;
import br.ufpe.cin.support.WriteFile;

/**
 * This class implements a NC controller
 * 
 * @author Eliardo Cláudio - ecf@cin.ufpe.br
 *
 */
public class NCController {

    private StateMachineEnum state;
    private MyTimer timer;
    private MySshConnector sshConnection;
    private RandomVariateGenerator randF;
    private RandomVariateGenerator randR;

    public NCController(MySshConnector sshConnection, RandomVariateGenerator failure, RandomVariateGenerator repair) {
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
	System.out.println("entrou no metodo isAlive do NC e retornou: " + aux);
	if (aux.equals("running")) {
	    return true;
	} else
	    return false;
    }

    /**
     * Shuts-down the NC
     */
    public void stopNC() {
	//FIXME - REIMPLEMENTAR vendo a dependencia!!
	this.getSshConnection().setCommand("service eucalyptus-nc stop");
	this.getSshConnection().sshCommand();
    }

    /**
     * Starts the NC
     * 
     */
    public void startNC() {
	//FIXME - REIMPLEMENTAR vendo a dependencia!!
	this.getSshConnection().setCommand("service eucalyptus-nc start");
	this.getSshConnection().sshCommand();
    }

    /**
     * State Machine
     * 
     * @throws InterruptedException
     */
    public void runNCStateMachine() throws InterruptedException {
	switch (this.getState()) {
	case RUNNING:
	    if (this.isAlive()) {
	    	WriteFile.logger("\n ---> O Serviço foi iniciado!" , "NCController_log.txt");
			int waitingTime = this.generateRandomFailureTime();
			this.setTimer(new MyTimer(waitingTime));
			this.setState(StateMachineEnum.TIMER_INJECT_FAILURE);
			WriteFile.logger("Tempo de falha gerado: " + waitingTime, "NCController_log.txt");
			WriteFile.logger(new Date().toString(), "NCController_log.txt");
		    } else {
			// Sleep again until the NC starts
		    }
		    break;

	case TIMER_INJECT_FAILURE:
	    if (this.getTimer().isExpired()) {
			this.stopNC();
			WriteFile.logger("Failed. NC: " + this.getSshConnection().getHost(), "NCController_log.txt");
			WriteFile.logger(new Date().toString(), "NCController_log.txt");
			Thread.sleep(30000); // 30 seconds sleep for the NC to actually stops
			this.setState(StateMachineEnum.FAILED);
		    } else {
			// Sleep again until the timer expires
		    }
		    break;

	case FAILED:
	    if (!this.isAlive()) {
			int waitingTime = this.generateRandomRepairTime();
			this.setTimer(new MyTimer(waitingTime));
			this.setState(StateMachineEnum.TIMER_REPAIR);
			WriteFile.logger("Tempo de reparo Gerado: " + waitingTime, "NCController_log.txt");
			WriteFile.logger(new Date().toString(), "NCController_log.txt");
		    } else {
			// // Sleep again until the NC stops
		    }
		    break;

	case TIMER_REPAIR:
	    if (this.getTimer().isExpired()) {
			this.startNC();
			WriteFile.logger("\n ---> O Servi�o parou!" , "NCController_log.txt");
			WriteFile.logger("Started. NC: " + this.getSshConnection().getHost(), "NCController_log.txt");
			WriteFile.logger(new Date().toString(), "NCController_log.txt");
			Thread.sleep(30000); // 30 seconds sleep for the NC to actually starts
			this.setState(StateMachineEnum.RUNNING);
		    } else {
			// Wait for the timer to expire
		    }
		    break;

	}
  }
}
