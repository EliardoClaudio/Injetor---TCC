package Interface;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.ufpe.cin.support.WriteFile;

public class Contador {

	public static void Disponivel() {
		Stream<String> lendoArq;
		
		try {
			BufferedReader leitor = new BufferedReader(new FileReader ("Node_log.txt"));
			lendoArq = leitor.lines();
			
			List<Object> lista = lendoArq.collect(Collectors.toList());
			
			//Pegando Valores do Up...
																																																					
			String up = "Up";
			int brtUpSegundos[] = null;
			if (lista != null) {
				ArrayList<String> brtUpHora = new ArrayList<String> ();
				ArrayList<String> brtUP = new ArrayList<String> ();
				for(int i = 0; i < lista.size(); i++){
					Object indice = lista.get(i);
					
					if(((String) indice).contains(up)){
						Object aux;
						aux = lista.get(i+2);
						String auxStr = aux.toString();
						brtUP.add(auxStr);
						//System.out.println(brtUP);
					}
					String aux[] = new String[6];

					for(String x : brtUP){
						aux = x.split(" ");
						brtUpHora.add(aux[3]);
						//System.out.println(brtUpHora);
					}
				}
				leitor.close();
				int segundos = 0;
				String aux[] = new String[6];	
				brtUpSegundos = new int[brtUpHora.size()];
				int b = 0;
				for (String s : brtUpHora){
					aux = s.split(":");
					segundos = 0;
					for (int i = 0; i < aux.length; i++){
						if (i == 0){
							segundos = segundos +(Integer.parseInt(aux[i]))*3600;
						}
						if (i == 1) {
							segundos = segundos +(Integer.parseInt(aux[i]))*60;
						}
						if (i == 2) {
							segundos = segundos +(Integer.parseInt(aux[i]));
						}
						
					}
				
					brtUpSegundos[b] = segundos;
					b = b+1;	
				}
				//System.out.println(brtUpSegundos[1]);
			}
			
			//Pegando Valores do Down...
			
			String down = "Down";
			int brtDownSegundos[] = null;
			if (lista != null) {
				ArrayList<String> brtDownHora = new ArrayList<String> ();
				ArrayList<String> brtDown = new ArrayList<String> ();
				for(int i = 0; i < lista.size(); i++){
					Object indice = lista.get(i);

					if(((String) indice).contains(down)){
						Object aux;
						aux = lista.get(i+2);
						String auxStr = aux.toString();
						brtDown.add(auxStr);
					}
					String aux[] = new String[6];
					
					for(String x : brtDown){
						aux = x.split(" ");
						brtDownHora.add(aux[3]);
						//System.out.println(brtDownHora);
					}
				}
				leitor.close();
				int segundos = 0;
				String aux[] = new String[6];	
				brtDownSegundos = new int[brtDownHora.size()];
				int b = 0;
				for (String s : brtDownHora){
					aux = s.split(":");
					segundos = 0;
					for (int i = 0; i < aux.length; i++){
						if (i == 0){
							segundos = segundos +(Integer.parseInt(aux[i]))*3600;
						}
						if (i == 1) {
							segundos = segundos +(Integer.parseInt(aux[i]))*60;
						}
						if (i == 2) {
							segundos = segundos +(Integer.parseInt(aux[i]));
						}	
					}
					//System.out.println(segundos);
					if (b < brtDownSegundos.length){
						brtDownSegundos[b] = segundos;
						b = b+1;
					}
				}
			}
			int diferenca[] = new int[brtDownSegundos.length];
			for (int i = 0; i < diferenca.length; i++) {
				diferenca[i] = brtDownSegundos[i] - brtUpSegundos[i];
			}
			//System.out.println(brtUpSegundos[0]);
			
			String diferencaHoras[] = new String[diferenca.length];
			String resultado = null;
			for (int x = 0; x < diferenca.length; x++) {
				int segundos = diferenca[x]; 
				int segundo = segundos % 60; 
				int minutos = segundos / 60; 
				int minuto = minutos % 60; 
				int hora = minutos / 60;
				diferencaHoras[x] = Integer.toString(hora)+":"+Integer.toString(minuto)+":"+Integer.toString(segundo);
				resultado = diferencaHoras[x];
			}
			//System.out.println(resultado);
			WriteFile.logger("Available time: "+ resultado,"Results_log.txt");
			
		} catch (IOException e) {
			System.out.println("File Not Found!");
			e.printStackTrace();
		}

	}

}
