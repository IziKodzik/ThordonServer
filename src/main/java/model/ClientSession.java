package model;

import model.DTOs.Request.ClientActionRequest;

public class ClientSession{

	private int userId;
	private ClientActionRequest lastAction;
	private int[] windowCoords;


	public ClientSession(ClientActionRequest actionRequest) {
		this.userId = actionRequest.getUserId();
	}


	public int[] getWindowCoords() {
		return windowCoords;
	}

	public void setWindowCoords(int[] windowCoords) {
		this.windowCoords = windowCoords;
	}


	@Override
	public int hashCode() {
		return userId;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ClientSession){
			return this.userId == ((ClientSession) obj).userId;
		}
		return false;
	}
}
