package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import observer.Signal;

public class GameServer extends Thread{

	public final static String SERVER_CLOSED = "Exit";
	public final static String START_GAME    = "Start_Game";
	public final static String FIRST_TURN    = "first";
	
	private static ArrayList<BufferedWriter> clients;
	private static byte numberOfPlayers = 0;
	private static ServerSocket server;
	
	public Signal server_logger;
	
	public boolean game_started = false;
	
	public static Random rng = new Random();
	
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
	
	public void sendToAllClients(BufferedWriter bwSaida, String msg) {
		
		try {
			if (clients == null || clients.size() == 0) 
				throw new Exception("ERROR! Server unitialized or no players connected");
			
			BufferedWriter bwS;
			
			if (msg == null) {
				server_logger.emit_signal("Um jogador foi desconectado...\n");
				clients.remove(bwSaida);
				numberOfPlayers--;
				return;
			}
	
			for (BufferedWriter bw : clients) {
				bwS = (BufferedWriter) bw;
				if (!(bwSaida == bwS)) {
					bw.write(msg + "\r\n");
					bw.flush();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void startGame() throws Exception {
		
		int starter = rng.nextInt(numberOfPlayers-1);
		
		int i = 0;
		
		for (BufferedWriter current : clients) {
			
			String msg = START_GAME;
			
			if (starter == i ) {
				msg += " " + FIRST_TURN;
			}
			current.write(msg + "\r\n");
			current.flush();
			i++;
		}
		
	}

	@Override
	public void run() {
		
		while(true) {
			try {
				
				if (numberOfPlayers == 2) {
					
					server_logger.emit_signal("Conectado os dois jogadores...\n");
					while(clients.size() != numberOfPlayers) {
						System.out.println("clients size = " + clients.size());
						System.out.println("numberOfPlayers = " + numberOfPlayers);
					}
					server_logger.emit_signal("Jogo iniciado com sucesso\n");
					
					game_started = true;
					startGame();
				}
				
				server_logger.emit_signal("Aguardando conexão...\n");
				Socket newConnection = server.accept();
				server_logger.emit_signal("Cliente conectado..." + newConnection.getInetAddress().getHostAddress() + "\n");
				
				if(game_started) {
					server_logger.emit_signal("Conexão recusada, jogo já está em progresso!");
					newConnection.close();
				}
				
				numberOfPlayers++;
				server_logger.emit_signal("Jogadores connectados: " + numberOfPlayers + "\n");
				ClientServerThread server = new ClientServerThread(newConnection);
				server.received_message_from_client.connect(this, "sendToAllClients");
				server.start();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void close() {
		sendToAllClients(null,SERVER_CLOSED);

		try {
			clients.clear();
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
