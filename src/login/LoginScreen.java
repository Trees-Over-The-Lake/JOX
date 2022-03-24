package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginScreen implements ActionListener {

	private JFrame     frame;
	private JPanel     panel;
	private JLabel     ipLabel;
	private JTextField ipTextField;
	private JLabel     portLabel;
	private JTextField portTextField;
	private JButton    button;
	
	public LoginScreen(String frameName) {
		this.panel = new JPanel();
		this.frame = new JFrame();
		
		frame.setTitle(frameName);
		frame.setSize(350,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		this.frame.add(panel);
		
		this.panel.setLayout(null);
		this.ipLabel = new JLabel("IP");
		this.ipLabel.setBounds(10,20,80,25);
		this.ipLabel.setVisible(true);
		this.panel.add(ipLabel);
		
		this.ipTextField = new JTextField(20);
		this.ipTextField.setBounds(100,20,165,25);
		this.ipTextField.setVisible(true);
		this.panel.add(ipTextField);
		
		this.portLabel = new JLabel("Port");
		this.portLabel.setBounds(10,50,80,25);
		this.portLabel.setVisible(true);
		this.panel.add(portLabel);
		
		this.portTextField = new JTextField();
		this.portTextField.setBounds(100,50,165,25);
		this.portTextField.setVisible(true);
		this.panel.add(portTextField);
		
		this.button = new JButton();
		this.button.setText("Login");
		this.button.setBounds(10,80,80,25);
		this.button.addActionListener(this);
		this.button.setVisible(true);
		this.panel.add(button);
		
		this.frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent action) {
		String ip   = this.ipTextField.getText();
		String port = this.portTextField.getText();
		
		System.out.println(ip);
		System.out.println(port);
	}
}
