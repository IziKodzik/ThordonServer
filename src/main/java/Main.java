
import config.VariablesProvider;

import model.DummyImage;

import server.*;
import server.endpoint.impl.IOSEndpoint;
import server.endpoint.impl.ThordonEndpoint;

import server.layer.impl.SecurityLayer;
import server.layer.impl.ServiceLayer;


import server.receiver.impl.ThordonReceiver;

import server.requestDirector.impl.ThordonDirector;


import javax.imageio.ImageIO;
import java.awt.*;

import java.io.File;

import java.io.IOException;


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



			ConnectionKeeper connectionKeeper = new ConnectionKeeper(
					new ThordonReceiver(),new ThordonDirector(new IOSEndpoint(),new ThordonEndpoint()),new SecurityLayer(),new ServiceLayer()
			);
			server.setConnectionKeeper(connectionKeeper);
			server.open();



		} catch (IOException | AWTException e) {
			e.printStackTrace();
		}

	}


}
