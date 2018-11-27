package br.ufpe.cin.testes.temp;

import java.io.IOException;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.controller.CLCController;
import br.ufpe.cin.support.MySshConnector;

public class TestCLCController {
	
	public static void Chamada(String login, String senha) throws InterruptedException, IOException {
		
		try{
			System.out.println("Testando estado do Controlador da Nuvem");
			//SSH credentials to the CLC Machine
			MySshConnector con1 = new MySshConnector("root", senha, login);
	
			//Here the user can set any distribution of the enumerator
			// randF - Random Failure Time between 3 and 20 minutes, mean value is 10 minutes
			RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(180000, 1200000, 600000);
			// randR - Random Repair Time between 3 and 20 minutes, mean value is 10 minutes
			RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(180000, 1200000, 600000);
	
			CLCController clc1 = new CLCController(con1, randF, randR);
			int i = 0;
			while (true) {
				clc1.runCLCStateMachine();
				Thread.sleep(50);
				if (i==0){
					i = i + 1;			 
				}
			}
			
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}