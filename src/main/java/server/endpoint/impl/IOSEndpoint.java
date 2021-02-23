package server.endpoint.impl;

import server.ConnectionData;
import server.endpoint.Endpoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;

public class IOSEndpoint
	implements Endpoint {


	@Override
	public String getName() {
		return "iOS";
	}

	@Override
	public boolean callBack(OutputStream output, ConnectionData connectionData) {
		try{

			byte[] response = connectionData.getResponse();
			output.write(ByteBuffer.allocate(4).putInt(response.length).array());
			output.write(response);
			InputStream s = connectionData.getSocket().getInputStream();
			byte[] xd = new byte[2];
			s.read(xd);
			return true;

		}catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}
}

