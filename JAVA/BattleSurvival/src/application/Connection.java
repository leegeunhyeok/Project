package application;

import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
	private ServerSocket serverSocket;
	private Socket socket;
	private boolean isServer;
	
	public Connection(final boolean isServer) {
		this.isServer = isServer;
	}
	
	public Socket createServer(final int port) {
		serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			return serverSocket.accept();
		} catch(Exception e) {
			return null;
		}
	}
	
	public Socket connectServer(final String host, final int port) {
		socket = null;
		try {
			socket = new Socket(host, port);
			return socket;
		} catch(Exception e) {
			return null;
		}
	}
	
	public boolean stopConnection() {
		boolean client = false, server = false;
		try {
			if(socket != null && !socket.isClosed()) {
				socket.close();
				client = true;
				System.out.println("Socket disconnected");
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Socket disconnect error");
		}
		
		try {
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
				server = true;
				System.out.println("Server socket disconnect error");
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Server socket disconnect  error");
		}
		
		return client && server;
	}
}
