package br.ufpe.cin.support;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
    Timer timer;
    boolean expired;

    public MyTimer(int miliseconds) {
	timer = new Timer();
	this.expired = false;
	timer.schedule(new RemindTask(), miliseconds);
	// this will run the RemindTask run() method after the miliseconds passed
    }

    public void setExpired(boolean aux) {
	this.expired = aux;
    }

    public boolean isExpired() {
	return this.expired;
    }

    class RemindTask extends TimerTask {
	@Override
	public void run() {
	    setExpired(true);
	}
    }

}