package server;

import server.layer.Layer;
import server.receiver.Receiver;
import server.endpoint.Endpoint;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectionKeeper {

	private Receiver receiver;
	private List<Layer> layerChain = new ArrayList<>();
	private Endpoint endpoint;


	public ConnectionKeeper(Receiver receiver, Endpoint endpoint, ArrayList<Layer> layerChain) {
		this.receiver = receiver;
		this.endpoint = endpoint;
		this.layerChain = layerChain;
	}
	public ConnectionKeeper(Receiver receiver, Endpoint endpoint, Layer... layers){
		this.receiver = receiver;
		this.endpoint = endpoint;
		this.layerChain = Arrays.asList(layers);
	}

	public byte[] connectionChain(Socket socket,ConnectionData connectionData) throws IOException {

		connectionData.setSocket(socket);
		connectionData.setRequest( receiver.receive(socket.getInputStream()));
		boolean proceed = true;
		for(int op = 0 ; op < layerChain.size() && proceed; ++ op)
			proceed = layerChain.get(op).process(connectionData);

		endpoint.callBack(socket.getOutputStream(),connectionData);
		socket.close();
		return connectionData.getResponse();
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public List<Layer> getLayerChain() {
		return layerChain;
	}

	public void setLayerChain(ArrayList<Layer> layerChain) {
		this.layerChain = layerChain;
	}
	public void addLayer(Layer layer){
		this.layerChain.add(layer);
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}
}
