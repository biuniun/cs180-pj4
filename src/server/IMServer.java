package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class IMServer extends Server{
	private static String accountFileDir;
	private static String conFileDir;
	private static String blockListDir;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;

	public IMServer(int port) throws IOException {
		super(port);
	}

	
	
}
