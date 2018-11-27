package br.ufpe.cin.testes.temp;

import java.io.IOException;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.controller.VMController;
import br.ufpe.cin.support.MySshConnector;

public class TestVMStateMachine {
	
	public static void Chamada(String login, String senha) throws InterruptedException, IOException {
		
		try{
			System.out.println("Testando estado do Controlador VM");
			// SSH credentials to the Cloud Controller Machine
			MySshConnector con1 = new MySshConnector("root", senha, login);
			// String aux = con1.startVM();
			// Thread.sleep(60000); // sleep 1 min for the VM to start

			// Here the user can set any distribution of the enumerator
			// randF - Random Failure Time between 2 and 10 minutes, mean value
			// is 5 minutes
			RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
			// randR - Random Repair Time between 1 and 10 minutes, mean value
			// is 5 minutes
			RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);

			VMController vm1 = new VMController(con1, randF, randR);
			int i = 0;
			while (true) {
				vm1.runVMStateMachine();
				Thread.sleep(50);
				if (i == 0) {
					i = i + 1;
				}
			}
		}catch (InterruptedException e1) {
			System.out.println("Tratamento de erro!");
			e1.printStackTrace();
		}
	}
}