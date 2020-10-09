package server.receiver.impl;

import server.receiver.Receiver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ThordonReceiver
	implements Receiver {

	@Override
	public byte[] receive(InputStream input) {
		byte[] request = new byte[4];
		try {
			input.read(request);
			request = new byte[ByteBuffer.wrap(request).asIntBuffer().get()];
			input.read(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return request;
	}
}
