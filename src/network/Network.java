package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.net.InetAddress;

public class Network {

	private static Socket socket;
	public static DataOutputStream dataOutput;
	public static DataInputStream dataInput;
	private static ServerSocket serverSocket;
	
	public static boolean accepted = false;
	
	public static ConnectionType currentConnection;
	
	public boolean connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			dataOutput = new DataOutputStream(socket.getOutputStream());
			dataInput = new DataInputStream(socket.getInputStream());
			accepted = true;
		} catch (IOException e) {
			System.out.println("Unable to connect to the address: " + ip + ":" + port + " | Starting a server");
			return false;
		}
		System.out.println("Successfully connected to the server.");
		currentConnection = ConnectionType.Client;
		return true;
	}
	
	public void initializeServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("server created with port " + port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		currentConnection = ConnectionType.Server;
	}
	
	public String listenForServerRequest() {
		Socket socket = null;
		String response = null;
		try {
			socket = serverSocket.accept();
			dataOutput = new DataOutputStream(socket.getOutputStream());
			dataInput = new DataInputStream(socket.getInputStream());
			accepted = true;
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public String receiveData() {
		String response = null;
		try {
			response = new String(dataInput.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public void sendData(String data) {
		try {
			dataOutput.writeBytes(data + '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
