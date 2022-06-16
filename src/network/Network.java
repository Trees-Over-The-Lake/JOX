package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

public class Network {

	private static Socket socket;
	private static DataOutputStream dataOutput;
	private static DataInputStream dataInput;
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
			System.out.println("server created with port " + port + " and Ip " + InetAddress.getByName("::1"));
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
	        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        response = entrada.readLine();
	        System.out.println("FROM SERVER: " + entrada.readLine());
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
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
