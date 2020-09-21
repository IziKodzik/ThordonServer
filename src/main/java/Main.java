import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		try {
			TCPServer server = new TCPServer(2137);
			server.open();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread.sleep(2000);
		new Thread(() -> {

			try {
				Socket s = new Socket("localhost",2137);
				InputStream i = s.getInputStream();
				OutputStream o = s.getOutputStream();
				o.write("XDDDD\n".getBytes());

				o.write("JEBLO?".getBytes());
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();

	}
}
