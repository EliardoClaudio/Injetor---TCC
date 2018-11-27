package VM2;

import java.io.IOException;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

import br.ufpe.cin.support.MySshConnector;

public class TestVMStateMachine3 {
	
	public static void Chamada3(String login, String senha) throws InterruptedException, IOException {
		try{
			System.out.println("Testando estado do Controlador VM3");
			// SSH credentials to the VM Machine
			MySshConnector con1 = new MySshConnector("root", senha, login);

			// Here the user can set any distribution of the enumerator
			// randF - Random Failure Time between 3 and 20 minutes, mean value
			// is 10 minutes
			RandomVariateGenerator randF = new ExponentialRandomVariateGenerator(180000, 1200000, 600000);
			// randR - Random Repair Time between 3 and 20 minutes, mean value
			// is 10 minutes
			RandomVariateGenerator randR = new ExponentialRandomVariateGenerator(180000, 1200000, 600000);

			VMController3 vm3 = new VMController3(con1, randF, randR);
			int i = 0;
			while (true) {
				vm3.runVMStateMachine3();
				Thread.sleep(50);
				if (i == 0) {
					i = i + 1;
				}
			}

		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}