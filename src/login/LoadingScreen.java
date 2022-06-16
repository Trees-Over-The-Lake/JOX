package login;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LoadingScreen extends JFrame {

	private static final long serialVersionUID = 1L;

	public LoadingScreen(String frameName) {
		ImageIcon loading = new ImageIcon("res/spinner.gif");
	    this.add(new JLabel("loading... ", loading, JLabel.CENTER));

	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(400, 300);
	    this.setVisible(true);
	    this.setResizable(false);
	    this.setLocationRelativeTo(null);
	    
	    this.setTitle(frameName);
	   
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
}
