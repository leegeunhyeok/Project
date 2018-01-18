package application;

import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
	private boolean isServer;
	
	public Connection(final boolean isServer) {
		this.isServer = isServer;
	}
	
	public Socket createServer(final int port) {
		try {
			ServerSocket server = new ServerSocket(port);
			Socket socket = server.accept();
			return socket;
		} catch(Exception e) {
			return null;
		}
	}
	
	public Socket connectServer(final String host, final int port) {
		try {
			Socket socket = new Socket(host, port);
			return socket;
		} catch(Exception e) {
			return null;
		}
	}
}
