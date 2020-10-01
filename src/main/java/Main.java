
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		int[] y = {1,2,3};
		System.out.println(Arrays.toString(y));
		xd(y);
		System.out.println(Arrays.toString(y));
		try {
			TCPServer server = new TCPServer(2137);
			ConnectionKeeper keeper = new ConnectionKeeper();
			keeper.addLayer(new DefaultLayer());
			server.setConnectionKeeper(keeper);
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

				byte[] mes = "jebac disa kurwe zwisa".getBytes();
				o.write(ByteBuffer.allocate(4).putInt(mes.length).array());
				o.write(mes);

				byte[] data = new byte[4];
				i.read(data);
				data = new byte[ByteBuffer.wrap(data).asIntBuffer().get()];
				i.read(data);
				System.out.println(new String(data));

			} catch (IOException e) {
				e.printStackTrace();
			}

		}).start();
	}

	public  static void xd(int[] a){
		a[2] = -23;
	}
}
