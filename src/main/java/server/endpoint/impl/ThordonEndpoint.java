package server.endpoint.impl;

import server.ConnectionData;
import server.endpoint.Endpoint;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ThordonEndpoint
	implements Endpoint {


	@Override
	public boolean send(OutputStream output, ConnectionData connectionData) {
		try{

			byte[] response = connectionData.getResponse();
			System.out.println(response.length);
			output.write(ByteBuffer.allocate(4).putInt(response.length).array());
			output.write(response);
			return true;

		}catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}
}
