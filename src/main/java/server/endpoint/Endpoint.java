package server.endpoint;

import server.ConnectionData;

import java.io.OutputStream;

public interface Endpoint {

	boolean send(OutputStream output, ConnectionData connectionData);

}

