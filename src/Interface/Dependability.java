package Interface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import br.ufpe.cin.support.WriteFile;

public class Dependability {
	/**
	 * @author Eliardo Cláudio - ecf@cin.ufpe.br
	 */
	public static void Result(long tempoTotalSegundos) {
			Stream<String> lendoArq = null;
			Object sUp = null;
			Object sDown = null;
			try {
				BufferedReader leitorUp = new BufferedReader(new FileReader ("Node_log.txt")); 
				lendoArq = leitorUp.lines();
	
				List<Object> listaUp = lendoArq.collect(Collectors.toList());
 			
				String testUp = "Up";
 			
	 		    if(listaUp!=null) {
	 		    	int count = 0;
	 		    	int aux1 = 0;
	 				for(int i=0; i < listaUp.size(); i++) {
	 					sUp = listaUp.get(i);
	 					if(((String) sUp).contains(testUp)) 
	 						count++;
	 					    aux1 = count;
	 			}
	 			leitorUp.close();
	 			
	 			BufferedReader leitorDown = new BufferedReader(new FileReader ("Node_log.txt")); 
				lendoArq = leitorDown.lines();
	
				List<Object> listaDown = lendoArq.collect(Collectors.toList());
 			
				String testDown = "Down";
 			
	 		    if(listaDown!=null) {
	 		    	int count2 = 0;
	 		    	int aux2 = 0;
	 				for(int i=0; i < listaDown.size(); i++) {
	 					sDown = listaDown.get(i);
	 					if(((String) sDown).contains(testDown)) 
	 						count2++;
	 					    aux2 = count2;
	 			}
	 			leitorDown.close();
	 			
	 			double up;
	 			up = (double) aux1; 
	 			
	 			double down;
	 			down = (double) aux2; 
	 			
	 			//Calcula disponibilidade
	 			double valor = (up/(up+down));
	 			String availability = String.format("%.4f", (valor*100));
	 			WriteFile.logger("\n", "Results_log.txt");	
	 			WriteFile.logger("Current Availability: " + availability + "%", "Results_log.txt");
	 			WriteFile.logger("\n", "Results_log.txt");
	 			
	 			//Calcula indisponibilidade
	 			double valor2 = (1-valor);
	 			String unavailability = String.format("%.4f", (valor2*100));
	 			WriteFile.logger("Current Unavailability: " + unavailability + "%", "Results_log.txt");
	 			WriteFile.logger("\n", "Results_log.txt");
	 			
	 			//Calcula tempoUp
	 			//while (sUp != "Down") {
		 			double valor3 = (valor*tempoTotalSegundos);
		 			String timeUp = String.format("%.3f", valor3);
		 			WriteFile.logger("Current TimeUp: " + timeUp, "Results_log.txt");
		 			WriteFile.logger("\n", "Results_log.txt");
	 			//}
	 			
	 			//Calcula tempoDown
	 			//while (sDown != "Up") {
		 			double valor4 = ((valor2)*tempoTotalSegundos);
		 			String timeDown = String.format("%.3f", valor4);
		 			WriteFile.logger("Current TimeDown: " + timeDown, "Results_log.txt");
		 			WriteFile.logger("\n", "Results_log.txt");
	 			//}
	 			
	 			//Calcula #9s - Alta disponibilidade	
	 			double valor5 = (-Math.log10(1-valor));
	 			String high_availability = String.format("%.3f", valor5);
	 			WriteFile.logger("Current #9s: " + high_availability, "Results_log.txt");
	 			WriteFile.logger("\n", "Results_log.txt");
	 			
	 		    }
	 		 }
	 		    
		   }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 
	}
}
