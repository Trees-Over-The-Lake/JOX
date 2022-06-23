package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import observer.Signal;

public class GameServer extends Thread{

	public final static String SERVER_CLOSED = "Exit";
	
	private static ArrayList<BufferedWriter> clients;
	private static byte numberOfPlayers = 0;
	private static ServerSocket server;
	
	public Signal server_logger;
	
	public GameServer(int port) {
		
		this.server_logger = new Signal(String.class);
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Unable to start the server at the port: " + port);
		}
		System.out.println("Successfully started server with port " + port);
	}
	
	public static void addClient(BufferedWriter bw) {
		if (clients == null)
			clients = new ArrayList<>();
		
		clients.add(bw);
	}

	public synchronized void initializeServer() {
		
		this.start();
	}
	
	public static void sendToAllClients(BufferedWriter bwSaida, String msg) throws Exception {
		
		if (clients == null || clients.size() == 0) 
			throw new Exception("ERROR! Server unitialized or no players connected");
		
		
		BufferedWriter bwS;

		for (BufferedWriter bw : clients) {
			bwS = (BufferedWriter) bw;
			if (!(bwSaida == bwS)) {
				bw.write(msg + "\r\n");
				bw.flush();
			}
		}
	}	

	@Override
	public void run() {
		
		while(true) {
			try {
				
				server_logger.emit_signal("Aguardando conexão...\n");
				Socket newConnection = server.accept();
				server_logger.emit_signal("Cliente conectado...\n");
				numberOfPlayers++;
				server_logger.emit_signal("Jogadores connectados: " + numberOfPlayers + "\n");
				Thread server = new ClientServerThread(newConnection);
				server.start();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
