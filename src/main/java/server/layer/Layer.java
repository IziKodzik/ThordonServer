package server.layer;

import server.ConnectionData;

public interface Layer <T> {

	T process(ConnectionData connectionData);

}
