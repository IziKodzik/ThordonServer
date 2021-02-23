package server.layer.impl;

import com.google.gson.Gson;

import config.VariablesProvider;
import model.ClientSession;
import model.DTOs.Request.ClientActionRequest;
import model.SpecialSpot;
import server.ConnectionData;
import server.layer.Layer;
import service.RobotService;
import util.GuardedQueue;
import util.ImageWorker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class ServiceLayer
	implements Layer<Boolean>{

	String resourcesPath = "src/resources";
	GuardedQueue<ClientActionRequest> requestQueue;
	ArrayList<ClientSession> sessions = new ArrayList<>();
	RobotService robot = new RobotService();
	int currentPosition = 0;
	Gson g = new Gson();
	volatile private ClientSession lastSession;

	public ServiceLayer() throws AWTException {
		this.requestQueue = new GuardedQueue<>();

	}

	@Override
	public synchronized Boolean process(ConnectionData connectionData) {


		ClientActionRequest actionRequest = g.
					fromJson(new String(connectionData.getRequest()),
							ClientActionRequest.class);
		this.requestQueue.put(actionRequest);
		connectionData.setResponse(this.serveClient(actionRequest));


		return true;
	}

	private byte[] serveClient(ClientActionRequest actionRequest){

		ClientSession currentSession = new ClientSession(actionRequest);
		System.out.println(actionRequest);
		if(this.lastSession!=null &&!this.lastSession.equals(currentSession) )
			lastSession.setInterrupted(true);

		int sessionIndex = this.sessions.indexOf(currentSession);
		if(sessionIndex != -1) {
			currentSession = this.sessions.get(sessionIndex);
			this.serveKnownClient(currentSession, actionRequest);
		}else
			this.serveNewClient(currentSession,actionRequest);

		int[] coords = currentSession.getWindowCoords();
		BufferedImage image = VariablesProvider.getDummyImage().getImage();
		image = robot.createScreenCapture(new Rectangle(coords[0],coords[1],image.getWidth(),image.getHeight()));
		this.lastSession = currentSession;
		java.util.List<SpecialSpot> specialSpots = VariablesProvider.getDummyImage().getSensitiveSpots();
		boolean se = specialSpots.stream().
				anyMatch(spot -> spot.getHitBox().contains(actionRequest.getX(),actionRequest.getY()));
		if(se)
			currentSession.setLastAction(actionRequest);

		currentSession.setLastAction(actionRequest);
		try {
			return ImageWorker.parseImageToByteArray(image,"png");
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	private void serveNewClient(ClientSession currentSession, ClientActionRequest actionRequest) {
		currentSession.setDesktopIndex(this.sessions.size());
		BufferedImage dummy = VariablesProvider.getDummyImage().getImage();

		this.currentPosition = 1;
		this.robot.openNewDesktop();
		this.sessions.add(currentSession);
		this.currentPosition = this.sessions.size()-1;
		try {
			Desktop.getDesktop().
					open(new File("C:\\Program Files (x86)\\Thordon Bearings Inc\\Thordon Bearing Sizing Calculation Program\\Thordon Bearing Sizing Calculation Program.exe"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(5000);
			this.robot.clickAltKey((char)(KeyEvent.VK_F4));
			this.robot.mouseMove(0,0);
			this.robot.leftClick();
			System.out.println("OUT");
			Thread.sleep(200);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {

			currentSession.setWindowCoords(ImageWorker.findCoords(dummy));
		} catch (IOException | AWTException e) {
			e.printStackTrace();
		}
	}

	private void closeSession(ClientSession clientSession){

		this.sessions.remove(clientSession);
		this.currentPosition--;
	}

	private void serveKnownClient(ClientSession currentSession, ClientActionRequest actionRequest) {
		if(currentSession.isInterrupted() && currentSession.getLastAction()!=null) {
			this.executeActionRequest(currentSession, actionRequest);
			currentSession.setInterrupted(false);
		}

		this.executeActionRequest(currentSession,actionRequest);
	}
	
	private void executeActionRequest(ClientSession currentSession,ClientActionRequest actionRequest){
		String command = actionRequest.getCommand();
		this.robot.centerDesktop(currentSession.getDesktopIndex() - this.currentPosition );
		System.out.println(command);
		if(command.equals("click")){
			if(!currentSession.equals(lastSession)){
				lastSession.setInterrupted(true);
			}
			this.robot.mouseMove(actionRequest.getX() + currentSession.getWindowCoords()[0]
					,actionRequest.getY() + currentSession.getWindowCoords()[1]);
			this.robot.leftClick();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else if(command.contains("fill:")){
			this.robot.clearTextField(actionRequest.getX() + currentSession.getWindowCoords()[0]
					,actionRequest.getY() + currentSession.getWindowCoords()[1]);
			String fill = command.substring(command.indexOf("fill:")+"fill:".length());
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.robot.fillTextField(actionRequest.getX() + currentSession.getWindowCoords()[0]
					,actionRequest.getY() + currentSession.getWindowCoords()[1],fill);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(fill);
		}

	}
}
