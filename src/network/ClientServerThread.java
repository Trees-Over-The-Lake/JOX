package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import observer.Signal;

public class ClientServerThread  extends Thread{
	
	private Socket con;
	private InputStream in;
	private InputStreamReader inr;
	private BufferedReader bfr;
	
	public Signal received_message_from_client;
	
	public ClientServerThread(Socket con) {
		received_message_from_client = new Signal(BufferedWriter.class, String.class);
        this.con = con;
        try {
            in  = con.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void run() {

		try {
			String msg;
			OutputStream ou = this.con.getOutputStream();
			Writer ouw = new OutputStreamWriter(ou);
			BufferedWriter bfw = new BufferedWriter(ouw);
			GameServer.addClient(bfw);
			
			msg = bfr.readLine();

			while (!GameClient.EXIT_MESSAGE.equalsIgnoreCase(msg) && msg != null) {
				System.out.println("Esperando ...");
				msg = bfr.readLine();
				System.out.println("Recebi " + msg);
				received_message_from_client.emit_signal(bfw,msg);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		
		interrupt();
	}


}
