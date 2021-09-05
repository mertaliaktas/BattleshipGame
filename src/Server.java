import java.net.*;

import javax.sound.sampled.Line;
import javax.swing.text.AsyncBoxView;

import java.io.*;

public class Server extends Thread {
	// initialize socket and input stream
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private DataInputStream input = null;
	private boolean isStart = false;
	private int port;
	Game game;

	// constructor with port
	public Server(int port) {
		this.port = port;
		start();
	}

	@Override
	public void run() {

		// starts server and waits for a connection
		try {
			server = new ServerSocket(port);
			System.out.println("Açýlan adres:"+server.getLocalSocketAddress());
			//server.bind("localhost", port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");
			setStart(true);

			game = new Game("Server", socket);

			// takes input from the client socket
			
			System.out.println("Closing connection");

			// close connection
			socket.close();
			in.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public boolean getStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
}
