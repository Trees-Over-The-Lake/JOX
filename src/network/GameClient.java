package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import observer.Signal;

public class GameClient extends Thread {

	public final static String EXIT_MESSAGE = "Exit";

	private static Socket socket;
	private static OutputStream ou;
	private static Writer ouw;
	private static BufferedWriter bfw;

	private static String ip;
	private static int port;

	private static String name;
	
	public static boolean connected = false;
	
	public Signal server_sended_message;

	public GameClient() throws Exception {
		
		server_sended_message = new Signal(String.class);
		
		if (!connected)
			throw new Exception("ERROR! Not connected");
		
	}
	
	public GameClient(String clientName, String clientIp, int clientPort) {
		
		server_sended_message = new Signal(String.class);
		
		name = clientName;
		ip   = clientIp;
		port = clientPort;
	}

	public void connect() {
		try {

			socket = new Socket(ip, port);
			ou = socket.getOutputStream();
			ouw = new OutputStreamWriter(ou);
			bfw = new BufferedWriter(ouw);
			bfw.write(name + "\r\n");
			bfw.flush();
			connected = true;
			
		} catch (Exception e) {
			System.err.println("ERROR! Conexão desconhecida fechando a aplicação");
			System.exit(1);
		}
	}

	public void sendData(String data) {

		try {
			System.out.println("enviado dados..." + data);
			bfw.write(data + "\r\n");
			bfw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Saido mensagem");
	}
	
	public synchronized void startServerCommunication() {
		
		this.start();
	}

	@Override
	public void run() {

		try {
			InputStream in = socket.getInputStream();
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader bfr = new BufferedReader(inr);
			String msg = "";

			while (!GameServer.SERVER_CLOSED.equalsIgnoreCase(msg)) {

				if (bfr.ready()) {
					msg = bfr.readLine();
					server_sended_message.emit_signal(msg);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		interrupt();
	}

	public void close() {
		sendData(EXIT_MESSAGE);

		try {
			bfw.close();
			ouw.close();
			ou.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
