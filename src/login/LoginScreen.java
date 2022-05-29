package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import observer.Signal;

import java.util.HashMap;

public class LoginScreen extends JFrame{

	private static final long serialVersionUID = 1L;
	
	// Map keys
	static final String IP_KEY        = "ip";
	static final String PORT_KEY      = "port";
	
	// Login signals
	public Signal create_server;
	public Signal login_server;
	
	// Java Swing components
	private JTabbedPane tabbedPanel;
	
	private JPanel     clientPanel;
	private JTextField clientIpTextField;
	private JTextField clientPortTextField;
	private JButton    loginClientButton;
	
	private JPanel     serverPanel;
	private JTextField serverPortTextField;
	private JButton    createServerButton;
	
	public LoginScreen(String frameName) {
		
		this.create_server = new Signal(HashMap.class);
		this.login_server  = new Signal(HashMap.class);
		
		this.tabbedPanel = new JTabbedPane();
		this.add(tabbedPanel);
		
		this.initFrame(frameName);
		this.initClientTab();
		this.initServerTab();
		
		this.tabbedPanel.add("Cliente",this.clientPanel);
		this.tabbedPanel.add("Servidor",this.serverPanel);
		
		this.setVisible(true);
	}
	
	/**
	 * Initiate the JFrame, by setting its parameters
	 * @param frameName its the name of the window
	 */
	public void initFrame(String frameName) {
		
		this.setTitle(frameName);
		this.setSize(350,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Initiate the client side tab
	 */
	public void initClientTab() {
		
		JLabel ipLabel;
		JLabel portLabel;
		
		this.clientPanel = new JPanel();
		this.clientPanel.setLayout(null);
		
		ipLabel = new JLabel("IP");
		ipLabel.setBounds(10,20,80,25);
		ipLabel.setVisible(true);
		this.clientPanel.add(ipLabel);
		
		this.clientIpTextField = new JTextField(20);
		this.clientIpTextField.setBounds(100,20,165,25);
		this.clientIpTextField.setVisible(true);
		this.clientPanel.add(this.clientIpTextField);
		
		portLabel = new JLabel("Port");
		portLabel.setBounds(10,50,80,25);
		portLabel.setVisible(true);
		this.clientPanel.add(portLabel);
		
		this.clientPortTextField = new JTextField();
		this.clientPortTextField.setBounds(100,50,165,25);
		this.clientPortTextField.setVisible(true);
		this.clientPanel.add(this.clientPortTextField);
		
		this.loginClientButton = new JButton();
		this.loginClientButton.setText("Login");
		this.loginClientButton.setBounds(10,80,80,25);
		this.loginClientButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
				/**
				 * Start game as client side
				 */
				HashMap<String,String> data=new HashMap<String,String>();
				data.put(IP_KEY, clientIpTextField.getText());
				data.put(PORT_KEY, clientPortTextField.getText());
				
				login_server.emit_signal(data);
				
			}
		});
		this.loginClientButton.setVisible(true);
		this.clientPanel.add(this.loginClientButton);
	}
	
	/**
	 * Initiate the client side tab
	 */
	public void initServerTab() {
		
		JLabel portLabel;
		
		this.serverPanel = new JPanel();
		this.serverPanel.setLayout(null);
		
		this.serverPortTextField = new JTextField(20);
		this.serverPortTextField.setBounds(100,20,165,25);
		this.serverPortTextField.setVisible(true);
		this.serverPanel.add(this.serverPortTextField);
		
		portLabel = new JLabel("Port");
		portLabel.setBounds(10,20,80,25);
		portLabel.setVisible(true);
		this.serverPanel.add(portLabel);
		
		this.createServerButton = new JButton();
		this.createServerButton.setText("Create server");
		this.createServerButton.setBounds(10,80,125,25);
		this.createServerButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
				/**
				 * Start game as server side
				 */
				HashMap<String,String> data=new HashMap<String,String>();
				data.put(IP_KEY, clientIpTextField.getText());
				data.put(PORT_KEY, clientPortTextField.getText());
				
				create_server.emit_signal(data);
			}
		});
		this.createServerButton.setVisible(true);
		this.serverPanel.add(this.createServerButton);
		
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
}
