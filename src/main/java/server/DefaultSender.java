package server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class DefaultSender
	implements Sender{
	@Override
	public boolean send(OutputStream output, byte[] response) {
		byte[] data = ByteBuffer.allocate(4).putInt(response.length).array();
		try {
			output.write(data);
			output.write(response);
			System.out.println(new String(response) + "<- sent");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
