package model.DTOs.Request;

public class ClientActionRequest {

    private String command;
    private int x;
    private int y;
    private int userId;


    public ClientActionRequest(String command, int x, int y, int userId) {
        this.command = command;
        this.x = x;
        this.y = y;
        this.userId = userId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

	@Override
	public String toString() {
		return "ClientActionRequest{" +
				"command='" + command + '\'' +
				", x=" + x +
				", y=" + y +
				", userId=" + userId +
				'}';
	}
}
