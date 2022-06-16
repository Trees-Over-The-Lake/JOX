package main;

import java.util.HashMap;
import java.util.Map;

import login.LoginScreen;
import network.Network;
import tic_tac_toe.MainGameLoop;
public final class JOX  {
	
	LoginScreen login;
	
	public JOX() {
		login = new LoginScreen("JOX");
		login.create_server.connect(this,"create_server");
		login.login_server.connect(this,"login_server");
	}
	
	public static void main(String[] args) {
		new JOX();
	}
	
	public void create_server(HashMap<String, String> content) {
		System.out.println("create_server");
		login.close();
		Network server = new Network();
		System.out.println(content.get(LoginScreen.PORT_KEY));
		server.initializeServer(Integer.parseInt(content.get(LoginScreen.PORT_KEY)));
		
		MainGameLoop t = new MainGameLoop("Tic Tac Toe");
	}
	
	public void login_server(HashMap<String, String> content) {
		System.out.println("login_server");
		login.close();
		Network client = new Network();
		client.connect(content.get(LoginScreen.IP_KEY), Integer.parseInt(content.get(LoginScreen.PORT_KEY)));
		
		MainGameLoop t = new MainGameLoop("Tic Tac Toe");
		// TODO: Logging into server logic
	}
}
