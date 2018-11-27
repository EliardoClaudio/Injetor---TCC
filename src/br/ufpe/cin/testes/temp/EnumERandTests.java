package br.ufpe.cin.testes.temp;

import com.gcap.randomvariategenerator.basics.randomvariatedistribution.ExponentialRandomVariateGenerator;
import com.gcap.randomvariategenerator.basics.randomvariatedistribution.RandomVariateGenerator;

public class EnumERandTests {
	public static void main(String[] args) throws InterruptedException {
		// teste da geracao aleatoria
		RandomVariateGenerator rand = new ExponentialRandomVariateGenerator(60000, 600000, 300000);

		while (true) {
			double b = rand.generateRandomNumber();
			// int c = (int) b;
			// System.out.println(new Date());
			System.out.println(b);
			Thread.sleep(1000);
		}
		// // teste do ENUM
		// java.util.List<Distributions> a =
		// Arrays.asList(Distributions.values());
		// if (a.get(0).ordinal() == 0) {
		// System.out.println("o enum foi: " + a.get(0).toString());
		// } else {
		// System.out.println("foi outro enum");
		// }

	}

}
