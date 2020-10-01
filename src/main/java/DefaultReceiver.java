import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class DefaultReceiver
	implements Receiver{
	@Override
	public byte[] receive(InputStream input) {

		try {
			byte[] data = new byte[4];
			int readBytes = input.read(data);
			int size = ByteBuffer.wrap(data).asIntBuffer().get();
			data = new byte[size];
			readBytes = input.read(data);
			System.out.println(new String(data) + " <- received message");
			return data;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
