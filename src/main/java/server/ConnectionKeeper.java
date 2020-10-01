package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionKeeper {

	private Receiver receiver;
	private List<Layer> layerChain = new ArrayList<>();
	private Sender sender;

	public ConnectionKeeper(){
		this.receiver = new DefaultReceiver();
		this.sender = new DefaultSender();
	}
	public ConnectionKeeper(Receiver receiver, Sender sender, ArrayList<Layer> layerChain) {
		this.receiver = receiver;
		this.sender = sender;
		this.layerChain = layerChain;
	}

	public byte[] connectionChain(Socket socket,ConnectionData connectionData) throws IOException {
		connectionData.request = receiver.receive(socket.getInputStream());
		boolean proceed = true;
		for(int op = 0 ; op < layerChain.size() && proceed; ++ op)
			proceed = layerChain.get(op).process(connectionData);

		System.out.println(connectionData.response.length);

		sender.send(socket.getOutputStream(),connectionData.response);
		socket.close();
		return connectionData.response;
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

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}
}
