

import jdk.internal.org.objectweb.asm.Handle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class TCPServer
	implements Runnable {


	private ConnectionKeeper connectionKeeper;
	private final ServerSocket serverSocket;
	private volatile boolean isRunning;
	private ExecutorService threadPool = Executors.newFixedThreadPool(25);
	private Thread mainThread;

	public TCPServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.isRunning = true;
		this.mainThread = new Thread(this);
	}

	public void setConnectionKeeper(ConnectionKeeper connectionKeeper) {
		this.connectionKeeper = connectionKeeper;
	}

	public ConnectionKeeper getConnectionKeeper() {
		return connectionKeeper;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}


	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean running) {
		isRunning = running;
	}


	public synchronized void open() {

			if(!isRunning){
				System.out.println("Server can not be opened after closing.");
				return;
			}
			System.out.println(String.format("Server started on port: %d.",serverSocket.getLocalPort()));
			this.mainThread.start();
	}

	public void close() throws IOException {
		if(!isRunning)
			return;
		this.serverSocket.close();
		this.mainThread.interrupt();
		this.isRunning = false;
	}

	public void serverLoop() throws IOException{

		while (this.isRunning && !this.mainThread.isInterrupted()) {
			System.out.println("Accepting requests.");
			Socket socket = serverSocket.accept();
			System.out.println(String.format("Received connection %s.",socket.toString()));
			threadPool.submit(new ConnectionHandle(socket,connectionKeeper));
		}
		this.threadPool.shutdown();
		System.out.println("Server stopped.");
	}


	@Override
	public void run() {
		try {
			serverLoop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ConnectionHandle
		implements Callable<String>{

		private Socket clientSocket;
		private ConnectionKeeper connectionKeeper;
		ConnectionData connectionData = new ConnectionData();

		public ConnectionHandle(Socket clientSocket, ConnectionKeeper connectionKeeper){
			this.connectionKeeper = connectionKeeper;
			this.clientSocket = clientSocket;
		}


		@Override
		public String call() throws Exception {
			return new String(connectionKeeper.connectionChain(clientSocket,connectionData));
		}

	}

}

