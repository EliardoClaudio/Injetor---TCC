package br.ufpe.cin.testes.temp;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.controller.HardwareController;
import br.ufpe.cin.support.MySshConnector;

public class TestHardwareController {
	public static void Chamada(String login, String senha) throws InterruptedException, IOException {
		
		try{
			System.out.println("Testando estado do Controlador de Hardware");
			// SSH credentials to the Hardware
			MySshConnector con1 = new MySshConnector("root", senha, login);

			// Here the user can set any distribution of the enumerator
			// randF - Random Failure Time between 2 and 10 minutes, mean value
			// is 5 minutes
			RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(120000, 600000, 300000);
			// randR - Random Repair Time between 1 and 10 minutes, mean value
			// is 5 minutes
			RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(60, 600, 300);

			HardwareController hw1 = new HardwareController(con1, randF, randR);

			int i = 0;
			while (true) {
				hw1.runHardwareStateMachine();
				Thread.sleep(50);
				if (i == 0) {
					JOptionPane.showMessageDialog(null, "Tentativa de Conexão realizada com sucesso!");
					i = i + 1;
				}
			}
		
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
