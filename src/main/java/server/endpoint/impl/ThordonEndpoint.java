package server.endpoint.impl;

import config.VariablesProvider;
import server.ConnectionData;
import server.endpoint.Endpoint;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ThordonEndpoint
	implements Endpoint {


	@Override
	public String getName() {
		return "web";
	}

	@Override
	public boolean callBack(OutputStream output, ConnectionData connectionData) {
		try{

			byte[] response = connectionData.getResponse();
			output.write(ByteBuffer.allocate(4).putInt(response.length).array());
			output.write(response);
			return true;

		}catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}
}
