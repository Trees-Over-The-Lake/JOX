package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
			System.out.println("ip: " + ip);
			System.out.println("port: " + port);
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
	
	public void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dataOutput = new DataOutputStream(socket.getOutputStream());
			dataInput = new DataInputStream(socket.getInputStream());
			accepted = true;
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
