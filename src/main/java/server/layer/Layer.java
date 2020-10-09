package server.layer;

import server.ConnectionData;

public interface Layer {

	boolean process(ConnectionData connectionData);

}
