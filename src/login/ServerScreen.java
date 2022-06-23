package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import network.GameServer;

public class ServerScreen extends JFrame{

	private static final long serialVersionUID = 3L;

	private JTextArea consoleLog;
	private JScrollPane scroll;
	private JPanel panelContent;
	
	private JButton closeServer;
	private JButton howToGetMyIp;
	
	GameServer server;
	
	public ServerScreen(String frameName, int port) {
		
		this.initFrame(frameName);
		
		this.panelContent = new JPanel();
		this.setContentPane(panelContent);
		
		this.initConsoleLog();
		this.initButtons();
		
		server = new GameServer(port);
		server.server_logger.connect(this, "updateConsoleLog");
		
		this.setVisible(true);
	}
	
	private void initButtons() {
		this.closeServer = new JButton("Close Server");
		this.closeServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
				System.exit(0);
			}
		});
		
		this.panelContent.add(closeServer);
		
		this.howToGetMyIp = new JButton("How to get my IP");
		this.howToGetMyIp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
				 JOptionPane.showMessageDialog(panelContent,
	                        "If you are on Linux, you can get your IP by typing: \nip a\n\n" + 
				            "If you are on Windows, type this in the CMD: \nipconfig\n\n" +
	                        "You can also access the website \"https://whatismyipaddress.com/\" to see your ip.", 
	                        "How to get your IP", 
	                        JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		this.panelContent.add(howToGetMyIp);
	}
	
	private void initConsoleLog() {
		JLabel consoleLabel = new JLabel("Console Log");
		this.panelContent.add(consoleLabel);
		
		this.consoleLog = new JTextArea(20,50);
		this.consoleLog.setEditable(false);
		this.consoleLog.setLineWrap(false);
		
		this.scroll = new JScrollPane(consoleLog);
		this.panelContent.add(scroll);
	}
	
	/**
	 * Initiate the JFrame, by setting its parameters
	 * @param frameName its the name of the window
	 */
	private void initFrame(String frameName) {
		
		this.setTitle(frameName);
		this.setSize(640,480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	public void updateConsoleLog(String data) {
		this.consoleLog.append(data);
	}
	
	public void startServer() {
		server.initializeServer();
	}
}
