package Interface;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import resultadosnode.ResultDownNode;
import resultadosnode.ResultUpNode;

public class Monitor extends JFrame {	
	/**
	 * @author Eliardo Cláudio - ecf@cin.ufpe.br
	 */
	private static final long serialVersionUID = 1L;
		JPanel middlePanel;
		static JTextArea display;
		static JTextArea display2;
		JScrollPane scroll;
		JScrollPane scroll2;
		static JComboBox<?> JComboBox1;
		static String max;
	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Monitor() {
			setIconImage(Toolkit.getDefaultToolkit().getImage("icones/titulo.png"));
			JComboBox1 = new JComboBox(new String[]{"Network"});
			middlePanel = new JPanel ();
		    middlePanel.add(JComboBox1);
		    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Monitor" ));
		    display = new JTextArea ( 30, 35 );
		    display2 = new JTextArea ( 30, 35 );
		    middlePanel.add(display2);
		    display.setEditable ( false );
		    display2.setEditable ( false );
		    scroll = new JScrollPane ( display );
		    scroll2 = new JScrollPane ( display2 );
		    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		    scroll2.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

		    middlePanel.add ( scroll );
		    middlePanel.add ( scroll2 );
		 
		    JFrame frame = new JFrame ();
		    frame.add ( middlePanel );
		    frame.pack ();
		    frame.setLocationRelativeTo ( null );
		    frame.setVisible ( true );
			
		}
		
		public static void main(String[] args) throws IOException {
			long tempoInicial = System.currentTimeMillis();
			Monitor tela = new Monitor();
			tela.getAccessibleContext();
		    while(true) {
		        long tempoFinal = System.currentTimeMillis();
				long tempoTotal = ( tempoFinal - tempoInicial );
				long segundos = ( tempoTotal / 1000 ) % 60;
				long segundosTotal = ( tempoTotal / 1000 );
				long minutos = ( tempoTotal / 60000 ) % 60;
				long horas = tempoTotal / 3600000;				
				long tempoTotalSegundos = segundosTotal;

		      	try {
			    	 String opcao = String.valueOf(JComboBox1.getSelectedItem());
			    	 opcao = opcao.toUpperCase();	
		             switch(opcao)
		             {
		             case "NETWORK":		            	 	
		             		PrintWriter writer1 = new PrintWriter("Results_log.txt");
			 				writer1.print("");
			 				writer1.close();
			 				ResultUpNode.Up();
			 				ResultDownNode.Down();
			 				//ResultFailNode.Fail();
			 				//ResultRepairNode.Repair();
			 				Dependability.Result(tempoTotalSegundos);
		             		display2.setText("");
		             		BufferedReader leitor1 = new BufferedReader(new FileReader ("Results_log.txt"));   
		             		max = leitor1.readLine();
		             		display2.append("Fault injection analysis is in progress...");
		             		display2.append("\n"+"\n");
		             		display2.append( String.format( "Total Time: " ) );
		             		display2.append( String.format( "%02d:%02d:%02d", horas, minutos, segundos ) );
		             		display2.append("\n");
		             		while (max != null){
		             			display2.append(max);
		             			max = leitor1.readLine(); 
		             			display2.append("\n");
		             		}
		             		leitor1.close();
		             		
		             		display.setText("");
		             		BufferedReader leitor2 = new BufferedReader(new FileReader ("Node_log.txt"));   
		             		max = leitor2.readLine();
		             		while (max != null){
		             			display.append(max); 
		             			max = leitor2.readLine(); 
		             			display.append("\n");
		             		}
		             		display.setCaretPosition(display.getText().length());
		             		leitor2.close();
		             	    break;

		             }
		       
		       		} catch (IOException e) {
		        }
		      	temporizador();
		    }
		    
		} 
		       	        
		public static void temporizador() {
			try {
	        	Thread.sleep(2000);
	        } 
			catch(InterruptedException e){
	        }
		} 
	}