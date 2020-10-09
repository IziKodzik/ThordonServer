package server;

import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ConnectionData{
	private byte[] request = new byte[0];
	private byte[] response = new byte[0];
	private Socket socket;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public byte[] getRequest() {
		return request;
	}

	public void setRequest(byte[] request) {
		this.request = request;
	}

	public byte[] getResponse() {
		return response;
	}

	public void setResponse(byte[] response) {
		this.response = response;
	}

	public void appendToRequest(byte[] data){
		request = mergeArrays(request,data);
	}
	public void appendToResponse(byte[] data){
		response = mergeArrays(response,data);
	}

	private byte[] mergeArrays(byte[] array1,byte[] array2){
		return ByteBuffer.allocate(array1.length + array2.length).put(array1).put(array2).array();
	}

}