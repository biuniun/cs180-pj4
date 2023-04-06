package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * IMServer: Managing data store and login verification.
 * 
 * @author LK Niu niu61
 * @version 2023-04-06
 *
 */

/** */
public class Server {
	int port;
	ServerSocket serverSocket;

	/**
	 * Setup the server socket for port listening. Return null if port is occupied,
	 * 
	 * @param port The port to listen for network traffic.
	 */
	public Server(int port) throws IOException {
		try {
			this.serverSocket = new ServerSocket(port);
			this.port = port;
		} catch (IOException e) {
			return;
		}
	}

	/**
	 * Get the connection from {@code serverSocket}. Return null if the connection
	 * establishment failed.
	 * 
	 * @return Connected Socket.
	 */
	public Socket getConnection() {
		try {
			return serverSocket.accept();
		} catch (IOException e) {
			return null;
		}
	}

}
