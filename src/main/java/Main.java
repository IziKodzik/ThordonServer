
import config.VariablesProvider;
import model.ClientSession;
import model.DummyImage;
import model.SpecialSpot;
import server.*;
import server.endpoint.impl.ThordonEndpoint;
import server.layer.Layer;
import server.layer.impl.SecurityLayer;
import server.layer.impl.ServiceLayer;
import server.receiver.Receiver;
import server.endpoint.Endpoint;
import server.receiver.impl.ThordonReceiver;
import service.RobotService;
import util.GuardedQueue;
import util.ImageWorker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {

		//test
//		try{
//
//			ClientSession s = new ClientSession();
//			System.out.println(s.equals(null));
//
//		}catch (Exception e){
//			e.printStackTrace();
//		}
		System.exit(69);
		//test ends
		VariablesProvider.setResourcesPath("src\\main\\resources\\");
		try {
			DummyImage dummyImage=  new DummyImage(ImageIO.read(new File(VariablesProvider.getResourcesPath() + "\\dummy.PNG")));
			for(int op = 91; op < 91+5*27 ; op+=27) {
				dummyImage.addSensitiveSpot(new Rectangle(142, op, 374, op+20));
			}
			dummyImage.addSensitiveSpot(new Rectangle(142,255,336,275));
			dummyImage.addSensitiveSpot(new Rectangle(142,321,336,341));
			dummyImage.addSensitiveSpot(new Rectangle(142,422,336,442));
			dummyImage.addSensitiveSpot(new Rectangle(535,91,669,111));

			VariablesProvider.setDummyImage(dummyImage);

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			TCPServer server = new TCPServer(2137);


			ConnectionKeeper connectionKeeper = new ConnectionKeeper(new ThordonReceiver(),
					new ThordonEndpoint(),
					new SecurityLayer(),
					new ServiceLayer());

			server.setConnectionKeeper(connectionKeeper);
			server.open();



		} catch (IOException | AWTException e) {
			e.printStackTrace();
		}

	}

}
