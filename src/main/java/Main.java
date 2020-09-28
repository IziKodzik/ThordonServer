
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		try {
			TCPServer server = new TCPServer(2137);
			server.open();
			server.addLayer(new DefaultLayer());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread.sleep(2000);
		new Thread(() -> {

			try {
				Socket s = new Socket("localhost",2137);
				InputStream i = s.getInputStream();
				OutputStream o = s.getOutputStream();

				new Thread(()-> {
					try {
						o.write("XDDDD\n".getBytes());
					o.write("JEBLO?".getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}).start();
				byte[] data = new byte[100];
				new Thread(()-> {
					try {

						byte[] mes = new byte[0];
						byte[] kox = new byte[20];
						for(int b = i.read(kox); b > 0; b = i.read(kox)){
							mes = ByteBuffer.allocate(mes.length + b).put(mes).put(Arrays.copyOf(kox,b)).array();
						}
						System.out.println(new String(mes));


					} catch (IOException e) {
						e.printStackTrace();
					}

				}).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();

	}
}
