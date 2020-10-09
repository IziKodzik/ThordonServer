package server.layer.impl;

import server.ConnectionData;
import server.layer.Layer;

public class SecurityLayer
	implements Layer {

	@Override
	public boolean process(ConnectionData connectionData) {
		if(connectionData.getSocket().getInetAddress().isLoopbackAddress()) {
			return true;
		}
		return false;	}

}
