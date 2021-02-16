package server.endpoint.impl;

import server.ConnectionData;
import server.endpoint.Endpoint;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
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
			System.out.println("ios endpoint");
			byte[] response =(Base64.getEncoder().encode(connectionData.getResponse()));
			output.write(response);
			return true;

		}catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}
}

