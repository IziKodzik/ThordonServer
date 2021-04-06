package server;

import server.layer.Layer;
import server.receiver.Receiver;
import server.endpoint.Endpoint;
import server.requestDirector.RequestDirector;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class ConnectionKeeper {

	private Receiver receiver;
	private RequestDirector requestDirector;
	private List<Layer<Boolean>> layerChain = new ArrayList<>();


	public ConnectionKeeper(Receiver receiver,
							RequestDirector requestDirector,List<Layer<Boolean>> layerChain) {
		this.receiver = receiver;
		this.requestDirector = requestDirector;
		this.layerChain = layerChain;
	}

	public ConnectionKeeper(Receiver receiver,RequestDirector director,
							Layer<Boolean>... layers){
		this(receiver,director, Arrays.asList(layers));
	}


	public byte[] connectionChain(Socket socket,ConnectionData connectionData) throws IOException {

		connectionData.setSocket(socket);
		byte[] rawMessage =  receiver.receive(socket.getInputStream());
		System.out.println(new String(rawMessage));
		connectionData.setRequest(rawMessage);
		boolean proceed = true;
		for(int op = 0 ; op < layerChain.size() && proceed; ++ op)
			proceed = layerChain.get(op).process(connectionData);


		System.out.println(requestDirector.redirect(connectionData));

		requestDirector.redirect(connectionData).callBack(socket.getOutputStream(),connectionData);
		socket.close();
		return connectionData.getResponse();
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public List<Layer<Boolean>> getLayerChain() {
		return layerChain;
	}

	public void setLayerChain(ArrayList<Layer<Boolean>> layerChain) {
		this.layerChain = layerChain;
	}
	public void addLayer(Layer<Boolean> layer){
		this.layerChain.add(layer);
	}


}
