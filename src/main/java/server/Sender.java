package server;

import java.io.OutputStream;

public interface Sender {

	public boolean send(OutputStream output, byte[] response);

}

