package resultadosfrontend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import br.ufpe.cin.support.WriteFile;

public class ResultDownFront {
	public static void Down() {
			Stream<String> lendoArq = null;
			try {
				BufferedReader leitor = new BufferedReader(new FileReader ("Frontend_log.txt")); 
				lendoArq = leitor.lines();
	
				List<Object> lista = lendoArq.collect(Collectors.toList());
				//System.out.println("Dados do arquivo txt: "+lista);
 			
				String test = "Down";
 			
	 		    if(lista!=null) {
	 		    	int count = 0;
	 		    	int aux1 = 0;
	 				for(int i=0; i < lista.size(); i++) {
	 					Object s = lista.get(i);
	 					if(((String) s).contains(test)) 
	 						count++;
	 					    aux1 = aux1 + count;
	 				}
	 			leitor.close();
	 				
	 			//System.out.println("A lista contém " + aux1 + " Ups...");
	 				//Thread.sleep(40000);
	 				WriteFile.logger("Downs: " + count +"", "Results_log.txt");
	 				
	 		   }
	 		    
		   }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
	}
}
