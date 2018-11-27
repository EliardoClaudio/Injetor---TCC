package br.ufpe.cin.support;

import java.io.IOException;

/**
 * This class implements a generic method that checks if a host is reachable on
 * the network
 * 
 * @author Vandi Alves - valn@cin.ufpe.br
 *
 */
public class SubnetCheck {
	
	private String hostname;
	private static final String os = System.getProperty("os.name").toLowerCase();
	
	public SubnetCheck(String hostname){
		this.hostname = hostname;
	}

	public boolean isAlive() throws IOException, InterruptedException {
		Process p1;
		if(os.indexOf("win") >= 0){
			p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 " + this.hostname);
		}
		else{
			p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 " + this.hostname);
		}
		int returnVal;
		boolean reachable;
		returnVal = p1.waitFor();
		reachable = (returnVal == 0);
		return reachable;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
}
