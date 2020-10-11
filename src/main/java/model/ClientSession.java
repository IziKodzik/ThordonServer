package model;

import model.DTOs.Request.ClientActionRequest;

public class ClientSession{

	private int userId;
	private ClientActionRequest lastAction;
	private int[] windowCoords;
	private int desktopIndex;


	public ClientSession(ClientActionRequest actionRequest) {
		this.userId = actionRequest.getUserId();
	}

	public ClientSession(int userId, ClientActionRequest lastAction, int[] windowCoords, int desktopIndex) {
		this.userId = userId;
		this.lastAction = lastAction;
		this.windowCoords = windowCoords;
		this.desktopIndex = desktopIndex;
	}

	public ClientSession(int userId, int[] windowCoords, int desktopIndex) {
		this.userId = userId;
		this.windowCoords = windowCoords;
		this.desktopIndex = desktopIndex;
	}

	public int[] getWindowCoords() {
		return windowCoords;
	}

	public void setWindowCoords(int[] windowCoords) {
		this.windowCoords = windowCoords;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public ClientActionRequest getLastAction() {
		return lastAction;
	}

	public void setLastAction(ClientActionRequest lastAction) {
		this.lastAction = lastAction;
	}

	public int getDesktopIndex() {
		return desktopIndex;
	}

	public void setDesktopIndex(int desktopIndex) {
		this.desktopIndex = desktopIndex;
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
