package br.ufpe.cin.testes.temp;

import java.io.IOException;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.controller.CCController;
import br.ufpe.cin.support.MySshConnector;

public class TestCCController {
	
	public static void Chamada(String login, String senha) throws InterruptedException, IOException {
		
		try{
			System.out.println("Testando o estado do Controlador CC1");
			//SSH credentials to the Cluster Controller Machine
			MySshConnector con1 = new MySshConnector("root", senha, login);
			
			// Aqui o usuário pode definir qualquer distribuição do enumerador
			// randF - Tempo de falha aleatória entre 2 e 10 minutos, o valor médio é de 5 minutos
			RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
			// randR - Tempo de Reparo Aleatório entre 1 e 10 minutos, o valor médio é de 5 minutos
			RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60000, 600000, 300000);
			
			CCController cc1 = new CCController(con1, randF, randR);
			int i = 0;
			while (true) {
				cc1.runCCStateMachine();
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