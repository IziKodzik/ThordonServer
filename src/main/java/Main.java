
import config.VariablesProvider;
import model.DummyImage;
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

public class Main {

	public static void main(String[] args) {

		//test
//		try{
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		System.exit(69);
		//test ends
		VariablesProvider.setResourcesPath("src\\main\\resources\\");
		try {
			VariablesProvider.setDummyImage(new DummyImage(ImageIO.read(new File(VariablesProvider.getResourcesPath() + "\\dummy.PNG"))));
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
