package server.layer.impl;

import server.ConnectionData;
import server.layer.Layer;

public class SecurityLayer
	implements Layer<Boolean> {

	@Override
	public Boolean process(ConnectionData connectionData) {
		if(connectionData.getSocket().getInetAddress().isLoopbackAddress())
			return true;

		//check password and login then true
		return true;

	}

}
