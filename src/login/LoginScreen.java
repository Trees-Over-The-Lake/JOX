package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class LoginScreen{

	private JFrame      frame;
	private JTabbedPane tabbedPanel;
	
	private JPanel     clientPanel;
	private JTextField clientIpTextField;
	private JTextField clientPortTextField;
	private JButton    loginClientButton;
	
	private JPanel     serverPanel;
	private JTextField serverPortTextField;
	private JButton    createServerButton;
	
	public LoginScreen(String frameName) {
		
		this.frame       = new JFrame();
		
		this.tabbedPanel = new JTabbedPane();
		this.frame.add(tabbedPanel);
		
		this.initFrame(frameName);
		this.initClientTab();
		this.initServerTab();
		
		this.tabbedPanel.add("Cliente",this.clientPanel);
		this.tabbedPanel.add("Servidor",this.serverPanel);
		
		this.frame.setVisible(true);
	}
	
	public void initFrame(String frameName) {
		this.frame.setTitle(frameName);
		this.frame.setSize(350,200);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
	}
	
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
				loginServer();
			}
		});
		this.loginClientButton.setVisible(true);
		this.clientPanel.add(this.loginClientButton);
	}
	
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
				createServer();
			}
		});
		this.createServerButton.setVisible(true);
		this.serverPanel.add(this.createServerButton);
		
	}
	
	public void createServer() {
		System.out.println("createServerButton");
	}
	
	public void loginServer() {
		System.out.println("loginServer");
		
		String ip   = this.clientIpTextField.getText();
		String port = this.clientPortTextField.getText();
		
		System.out.println(ip);
		System.out.println(port);
	}
}
