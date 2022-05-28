package main;

import java.util.HashMap;
import java.util.Map;

import login.LoginScreen;
public class JOX  {
	
	public JOX() {
		LoginScreen login = new LoginScreen("JOX");
		login.create_server.connect(this,"create_server");
		login.login_server.connect(this,"login_server");
	}
	
	public static void main(String[] args) {
		new JOX();
	}
	
	public void create_server(HashMap<String, String> content) {
		System.out.println("create_server");
		for(Map.Entry<String,String> entry : content.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
		// TODO: Creating server logic
	}
	
	public void login_server(HashMap<String, String> content) {
		System.out.println("login_server");
		for(Map.Entry<String,String> entry : content.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
		// TODO: Logging into server logic
	}
}
