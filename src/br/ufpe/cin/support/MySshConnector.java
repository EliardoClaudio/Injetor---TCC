package br.ufpe.cin.support;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class MySshConnector {
    public String user;
    public String password;
    public String host;
    public String command;

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getSenha() {
	return password;
    }

    public void setSenha(String senha) {
	this.password = senha;
	MyUserInfo.passwd = senha;
    }

    public String getHost() {
	return host;
    }

    public void setHost(String host) {
	this.host = host;
    }

    public String getCommand() {
	return command;
    }

    public void setCommand(String command) {
	this.command = command;
    }

    public MySshConnector(String user, String senha, String host) {
	this.user = user;
	this.password = senha;
	this.host = host;
	MyUserInfo.passwd = senha;
    }

    public MySshConnector(String user, String senha, String host, String command) {
	this.user = user;
	this.password = senha;
	this.host = host;
	this.command = command;
	MyUserInfo.passwd = senha;
    }

    private static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
	public static String passwd;

	@Override
	public String getPassword() {
	    return passwd;
	}

	@Override
	public boolean promptYesNo(String str) {
	    return true;
	}

	@Override
	public String getPassphrase() {
	    return null;
	}

	@Override
	public boolean promptPassphrase(String message) {
	    return true;
	}

	@Override
	public boolean promptPassword(String message) {
	    return true;
	}

	@Override
	public void showMessage(String message) {
	}

	@Override
	public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt,
		boolean[] echo) {
	    return null;
	}

    }

    /**
     * Executes whatever is set in this.command via SSH
     * 
     * @return - the output of the command
     */
    public String sshCommand() {
	String nada = "zero";
	try {
	    JSch jsch = new JSch();

	    Session session = jsch.getSession(this.getUser(), this.getHost(), 22);

	    UserInfo ui = new MyUserInfo();
	    session.setUserInfo(ui);
	    session.connect();

	    Channel channel = session.openChannel("exec");
	    ((ChannelExec) channel).setCommand(this.getCommand());

	    channel.setInputStream(null);

	    ((ChannelExec) channel).setErrStream(System.err);

	    InputStream in = channel.getInputStream();

	    channel.connect();

	    byte[] tmp = new byte[1024];
	    while (true) {
		while (in.available() > 0) {
		    int i = in.read(tmp, 0, 1024);
		    if (i < 0)
			break;
		    String saida = new String(tmp, 0, i);
		    if (saida == null || saida.equals("")) {
			saida = "zero";
			return saida;
		    } else {
			return saida;
		    }
		}
		if (channel.isClosed()) {
		    if (in.available() > 0)
			continue;
		    //System.out.println("exit-status: " +
		    //channel.getExitStatus());
		    break;
		}
		try {
		    Thread.sleep(1000);
		} catch (Exception ee) {
		}
	    }
	    channel.disconnect();
	    session.disconnect();
	} catch (Exception e) {
	    System.out.println(e);
	}
	return nada;
    }

    /**
     * Shutdown the hardware
     */
    public void sendShutdown() {
	this.setCommand("shutdown -h now");
	this.sshCommand();
    }

    /**
     * Shuts-down the VM
     * 
     * @param vmId
     *            - the instance id
     */
    public void stopVM(String vmId) {
	this.setCommand("euca-terminate-instances " + vmId);
	this.sshCommand();
    }

    /**
     * Starts a new VM
     * 
     * @return the instance ID
     */
    public String startVM() {
    System.out.println("Tentei me conectar!");
	this.setCommand("euca-run-instances -k teste2 -t m1.small emi-D89C3AA2 | awk '{print $2}' | grep i-");
	System.out.println("Passou!");
	String aux = this.sshCommand().trim();
	return aux;
    }

    /**
     * Checks if the given VM is dead or alive
     * 
     * @param vmId
     *            - the instance id
     * @return true if the VM is running, otherwise returns false
     */
    public boolean vmAlive(String vmId) {
	this.setCommand("euca-describe-instances | grep " + vmId + " | awk '{print $6}'");
	String aux = this.sshCommand().trim();
	if (aux.equals("running")) {
	    return true;
	} else
	    return false;
    }

	public static String getText() {
		// TODO Auto-generated method stub
		return null;
	}

}
