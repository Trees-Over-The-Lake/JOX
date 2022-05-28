package main;

import java.util.HashMap;

import login.LoginScreen;
public class JOX  {
	
	public JOX() {
		LoginScreen login = new LoginScreen("JOX");
		login.events.connect("create_server", this);
		login.events.connect("login_server", this);
	}
	
	public static void main(String[] args) {
		JOX j = new JOX();
	}
	
	public void create_server(HashMap<String, String> content) {
		// TODO: Creating server logic
	}
	
	public void login_server(HashMap<String, String> content) {
		// TODO: Logging into server logic
	}
}
