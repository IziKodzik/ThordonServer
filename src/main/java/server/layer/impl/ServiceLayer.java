package server.layer.impl;

import com.google.gson.Gson;

import config.VariablesProvider;
import model.ClientSession;
import model.DTOs.Request.ClientActionRequest;
import server.ConnectionData;
import server.layer.Layer;
import service.RobotService;
import util.GuardedQueue;
import util.ImageWorker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class ServiceLayer
	implements Layer {

	String resourcesPath = "src/resources";
	GuardedQueue<ClientActionRequest> requestQueue;
	ArrayList<ClientSession> sessions = new ArrayList<>();
	RobotService robot = new RobotService();
	int currentPosition = 0;
	Gson g = new Gson();

	public ServiceLayer() throws AWTException {
		this.requestQueue = new GuardedQueue<>();

	}

	@Override
	public boolean process(ConnectionData connectionData) {

		ClientActionRequest actionRequest = g.
				fromJson(new String(connectionData.getRequest()),
						ClientActionRequest.class);
		this.requestQueue.put(actionRequest);
		BufferedImage dummy = VariablesProvider.getDummyImage().getImage();
		this.serveClient(actionRequest);
//		try {
//
//			ClientActionRequest clientActionRequest = g.fromJson(new String(connectionData.getRequest()), ClientActionRequest.class);
//			ClientSession currentSession = new ClientSession(clientActionRequest);
//
//
//		if(sessions.contains(currentSession)){
//			currentSession = sessions.get(sessions.indexOf(currentSession));
//			System.out.println("Hello old client");
//
//		}else{
//			if(sessions.size() != 0){
//				System.out.println("Open new window");
//			}
//			int[] i = ImageWorker.findCoords(dummy);
//			System.out.println(Arrays.toString(i) + "XDD");
//			currentSession.setWindowCoords(i);
//			this.sessions.add(currentSession);
//
//			System.out.println("Hello new client");
//		}
//		robot.mouseMove(clientActionRequest.getX() + currentSession.getWindowCoords()[0],
//				clientActionRequest.getY() + currentSession.getWindowCoords()[1]);
//		robot.leftClick();
//		Thread.sleep(200);
//
//		BufferedImage image = robot.createScreenCapture();
//		image = image.getSubimage(currentSession.getWindowCoords()[0],currentSession.getWindowCoords()[1],dummy.getWidth(),dummy.getHeight());
//		ByteArrayOutputStream imagesBytes = new ByteArrayOutputStream();
//		try {
//			ImageIO.write(image,"png",imagesBytes);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		connectionData.setResponse(imagesBytes.toByteArray());
//		System.out.println(actionRequest);
//		} catch (IOException | AWTException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return true;
	}

	private void serveClient(ClientActionRequest actionRequest){

		ClientSession currentSession = new ClientSession(actionRequest);

		int sessionIndex = this.sessions.indexOf(currentSession);
		if(sessionIndex != -1) {
			currentSession = this.sessions.get(sessionIndex);
			this.serveKnownClient(currentSession, actionRequest);
		}else
			this.serveNewClient(currentSession,actionRequest);

	}

	private void serveNewClient(ClientSession currentSession, ClientActionRequest actionRequest) {
		currentSession.setDesktopIndex(this.sessions.size());
		this.robot.openNewDesktop();
		this.sessions.add(currentSession);
		this.currentPosition = this.sessions.size()-1;
		try {
			Desktop.getDesktop().
					open(new File("P:\\Help Input for 2017 Sizing Program\\Thordon Bearing Sizing Calculation Program.exe"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void serveKnownClient(ClientSession currentSession, ClientActionRequest actionRequest) {
		this.robot.centerDesktop(this.currentPosition + currentSession.getDesktopIndex());
		System.out.println("Request dealing");
	}
}
