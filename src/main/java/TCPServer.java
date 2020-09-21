

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


	private final ServerSocket serverSocket;
	private volatile boolean isRunning;
	private List<Layer> layers = new ArrayList<>();
	ExecutorService threadPool = Executors.newFixedThreadPool(25);
	Thread mainThread;

	public TCPServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.isRunning = true;
		this.mainThread = new Thread(this);
	}

	public synchronized  void open() throws IOException{
			if(!isRunning){
				System.out.println("Server can not be opened after closing.");
				return;
			}

			System.out.println(String.format("Server started on port: %d.",serverSocket.getLocalPort()));
			this.mainThread.start();

	}

	public void close() throws IOException{
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
			threadPool.submit(new Handle(socket,layers));
			System.out.println(String.format("Received connection %s.",socket.toString()));
		}
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


	private static class Handle
			implements Callable<String>{

		private List<Layer> layers;
		private Socket socket;
		private InputStream input;
		private OutputStream output;
		private byte[] request = new byte[0];
		private byte[] response = new byte[0];

		public Handle(Socket socket,List<Layer> layers) throws IOException{
			this.socket = socket;
			this.input = socket.getInputStream();
			this.output = socket.getOutputStream();
			this.layers = layers;
		}

		@Override
		public String call() throws Exception {

			byte[] data = new byte[10];
			for(int size = this.input.read(data); size > 0; size = this.input.read(data)){
				request = ByteBuffer.allocate(request.length+size)
						.put(request).put(Arrays.copyOf(data,size)).array();
			}

			output.write(response);
			System.out.println(new String(request));
			return null;
		}



	}

}

