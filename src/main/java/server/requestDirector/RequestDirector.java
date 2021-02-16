package server.requestDirector;

import server.ConnectionData;
import server.endpoint.Endpoint;

public interface RequestDirector {

	Endpoint redirect(ConnectionData data);

}
