package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class GameClient {

	public final static String EXIT_MESSAGE = "Exit";

	private static Socket socket;
	private static OutputStream ou;
	private static Writer ouw;
	private static BufferedWriter bfw;

	private static String ip;
	private static int port;

	private static String name;
	
	public static boolean connected = false;

	public GameClient() throws Exception {
		
		if (connected == false) {
			throw new Exception("ERROR! Not connected");
		}
	}
	
	public GameClient(String clientName, String clientIp, int clientPort) {
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
			e.printStackTrace();
		}
	}

	public void sendData(String data) {

		try {
			bfw.write("Desconectado \r\n");
			bfw.write(data + "\r\n");
			bfw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String listenToServer() {

		String serverMessage = new String();
		try {
			InputStream in = socket.getInputStream();
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader bfr = new BufferedReader(inr);
			String msg = "";

			while (!GameServer.SERVER_CLOSED.equalsIgnoreCase(msg))

				if (bfr.ready()) {
					msg = bfr.readLine();
					if (msg.equals(GameServer.SERVER_CLOSED))
						serverMessage = "Servidor caiu! \r\n";
					else
						serverMessage = "\r\n";
				}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serverMessage;
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
