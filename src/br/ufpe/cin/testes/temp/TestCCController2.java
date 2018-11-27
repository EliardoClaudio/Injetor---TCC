package br.ufpe.cin.testes.temp;

import java.io.IOException;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.controller.CCController2;
import br.ufpe.cin.support.MySshConnector;

public class TestCCController2 {
	
	public static void Chamada2(String login, String senha) throws InterruptedException, IOException {
		
		try{
			System.out.println("Testando o estado do Controlador CC2");
				//SSH credentials to the Cluster Controller Machine
				MySshConnector con1 = new MySshConnector("root", senha, login);
			
				//Here the user can set any distribution of the enumerator
				// randF - Random Failure Time between 2 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
				// randR - Random Repair Time between 1 and 10 minutes, mean value is 5 minutes
				RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
			
				CCController2 cc2 = new CCController2(con1, randF, randR);
				int i = 0;
				while (true) {
				    cc2.runCCStateMachine2();
				    Thread.sleep(50);
					if (i==0){
						i = i + 1;			 
				    }
				}
				
		}catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}