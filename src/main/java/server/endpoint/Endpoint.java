package server.endpoint;

import server.ConnectionData;

import java.io.OutputStream;

public interface Endpoint {

	String getName();

	boolean callBack(OutputStream output, ConnectionData connectionData);

}

