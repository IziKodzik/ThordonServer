package server;

import java.nio.ByteBuffer;

public class DefaultLayer
	implements Layer{

	@Override
	public boolean process(ConnectionData connectionData) {
		String mess = " default layer visited";
		connectionData.response = ByteBuffer.allocate(connectionData.request.length + mess.length()).put(connectionData.request).put(mess.getBytes()).array();
		System.out.println("visited default layer");
		return true;
	}
}
