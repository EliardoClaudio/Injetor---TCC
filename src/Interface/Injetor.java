package Interface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import VM.TestVMStateMachine2;
import VM2.TestVMStateMachine3;
import br.ufpe.cin.testes.temp.TestCCController;
import br.ufpe.cin.testes.temp.TestCCController2;
import br.ufpe.cin.testes.temp.TestCCController3;
import br.ufpe.cin.testes.temp.TestCCController4;
import br.ufpe.cin.testes.temp.TestCLCController;
import br.ufpe.cin.testes.temp.TestHardwareController;
import br.ufpe.cin.testes.temp.TestVMStateMachine;

public class Injetor extends JFrame {
    /**
	 * @author Eliardo Cláudio - ecf@cin.ufpe.br
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<?> jComboBox1;
	private JTextField tfLogin;
	private JTextField tfTempo;
	private JTextField transition1;
	private JTextField transition2;
	private JTextField transition3;
	private JTextField transition4;
    private JLabel lbSenha; 
    private JLabel lbLogin;
    private JLabel lbTempo;
    private JLabel distribution;
    private JComboBox<?> c_distribution;
    private JButton btLogar;
    private JButton btCancelar;
    private JPasswordField pfSenha;
    private JLabel lbTime;
    private JComboBox<?> tfTime;
    private static Injetor frame;
    
    public Injetor() {
        inicializarComponentes();
        definirEventos();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void inicializarComponentes() {
    	this.setResizable(false);
        setTitle("SIMF Tool");
        setIconImage(Toolkit.getDefaultToolkit().getImage("icones/titulo.png"));
        setBounds(0,0,330,420); //Tamanho da Interface
        setLayout(null);
        jComboBox1 = new JComboBox(new String[]{"HW + VM", "HW", "PING IP"});
        tfTime = new JComboBox(new String[]{"Unlimited", "1 Day", "2 Days", "3 Days", "8 Days", "12 Days"});
        c_distribution = new JComboBox(new String[]{"EXPONENTIAL", "NORMAL", "TRIANGULAR", "UNIFORM"});
        transition1 = new JTextField(5);
        transition2 = new JTextField(5);
        transition3 = new JTextField(5);
        transition4 = new JTextField(5);
        tfLogin = new JTextField(5);
        tfTempo = new JTextField(5);
        pfSenha = new JPasswordField(5);
        lbSenha = new JLabel("Password:");
        lbLogin = new JLabel("IP Server:");
        lbTempo = new JLabel("Type Test:");
        btLogar = new JButton("Connect");
        btCancelar = new JButton("Cancel");
        lbTime = new JLabel("Test Duration:");
        distribution = new JLabel("Distribution:");
        tfLogin.setBounds(140, 40, 120, 25);
        lbLogin.setBounds(67, 40, 80, 25); 
        jComboBox1.setBounds(140, 190, 120, 25);
        lbTempo.setBounds(63, 190, 80, 25);
        lbSenha.setBounds(60, 220, 80, 25);
        pfSenha.setBounds(140, 220, 120, 25);
        lbTime.setBounds(42, 250, 80, 25);
        distribution.setBounds(52, 280, 80, 25);
        c_distribution.setBounds(140, 280, 120, 25);
        tfTime.setBounds(140, 250, 120, 25);
        btLogar.setBounds(50, 345, 100, 25);
        btCancelar.setBounds(170, 345, 100, 25);
        transition1.setBounds(140, 70, 120, 25);
        transition2.setBounds(140, 100, 120, 25); 
        transition3.setBounds(140, 130, 120, 25); 
        transition4.setBounds(140, 160, 120, 25);
        
        add(tfTime);
        add(lbTime);
        add(tfLogin);
        add(tfTempo);
        add(lbSenha);
        add(lbLogin);
        add(lbTempo);
        add(btLogar);
        add(btCancelar);
        add(pfSenha);
        add(jComboBox1);
        add(distribution);
        add(c_distribution);
        add(transition1);
        add(transition2);
        add(transition3);
        add(transition4);
        
    }

    public void definirEventos() {
        btLogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	ArrayList<String> listaOpcoes = new ArrayList<String>();
		        String aux1 = transition1.getText();
		        String aux2 = transition2.getText();
		        String aux3 = transition3.getText();
		        String aux4 = transition4.getText();
		        
		        listaOpcoes.add(aux1+"\n"+aux2+"\n"+aux3+"\n"+aux4);
		        System.out.println(listaOpcoes);

                String senha = String.valueOf(pfSenha.getPassword());
                String opcao = String.valueOf(jComboBox1.getSelectedItem()); 
				@SuppressWarnings("unused")
				String opcaoTime = String.valueOf(tfTime.getSelectedItem());
				
				String opcaoDistribution = String.valueOf(c_distribution.getSelectedItem());
				
				if (opcaoDistribution == "EXPONENTIAL") {
					//Distribution.EXPONENTIAL();
				}
                
                try {
                	String login = tfLogin.getText();
                	System.out.println("IP: "+ login);
                	System.out.println("Senha utilizada: "+senha);
                    opcao = opcao.toUpperCase();
                    frame.setVisible(false);
                    
                    switch(login)
                    {
	                    case "192.168.0.151":
	                    	System.out.println("Entrou no Front...");
	                	    TestCLCController.Chamada(login,senha);
	                	    break;
                    
                    	case "192.168.0.152":
                    		System.out.println("Entrou no Node...");
                    		TestCCController.Chamada(login,senha);
                    	    break;
                    	    	
                    	case "192.168.0.153":
                    		System.out.println("Entrou no Node2...");
                    		TestCCController2.Chamada2(login,senha);
                    	    break;
                    	    
                    	case "192.168.0.155":
	                    	System.out.println("Entrou no Node3...");
	                    	TestCCController3.Chamada3(login,senha);
	                	    break;
	                	    
                    	case "192.168.0.156":
	                    	System.out.println("Entrou no Node4...");
	                    	TestCCController4.Chamada4(login,senha);
	                	    break;
                    	    
                    	case "192.168.0.170":
                    		System.out.println("Entrou na VM1");
                    		TestVMStateMachine.Chamada(login,senha);
                    		break;

                		case "192.168.0.171":
                    		System.out.println("Entrou na VM2");
                    		TestVMStateMachine2.Chamada2(login,senha);
                    		break;
                    	
                		case "192.168.0.172":
                    		System.out.println("Entrou na VM3");
                    		TestVMStateMachine3.Chamada3(login,senha);
                    		break;
                    		
                    }
                    	switch(opcao)
                    	{
	                    	case "HARDWARE":
	                    		String aux = "192.168.0.151";
	                    		login = aux; 
	                    		System.out.println("Entrou no Hardware: " + login);
	                    	    TestHardwareController.Chamada(login,senha);
	                    	    break;  
                    	}
                    	//Thread.sleep(20000);
                    	
					}
                    catch (InterruptedException e1) {
                    	System.out.println("Tratamento de erro!");
						e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("Tratamento de erro!");
						e1.printStackTrace();
					}               
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
		public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
		             frame = new Injetor();
		             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		             Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
		             frame.setLocation((tela.width - frame.getSize().width) / 2,
		                     (tela.height - frame.getSize().height) / 2);
		             frame.setVisible(true);
            }
        });      
	}
}