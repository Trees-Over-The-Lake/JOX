package main;

import java.util.HashMap;

import login.LoginScreen;
import login.ServerScreen;
import network.GameClient;
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
		login.close();
		
		int port = Integer.parseInt(content.get(LoginScreen.PORT_KEY));

	    ServerScreen ss = new ServerScreen("Server",port);
	    
	    ss.startServer();
		//LoadingScreen ls = new LoadingScreen("Please wait until a user connect...");
		//ls.close();		
		
	}
	
	public void login_server(HashMap<String, String> content) {
		System.out.println("login_server");
		login.close();
		GameClient client = new GameClient("name", content.get(LoginScreen.IP_KEY), Integer.parseInt(content.get(LoginScreen.PORT_KEY)));
		client.connect();
		
		new MainGameLoop("Tic Tac Toe");
	}
}
