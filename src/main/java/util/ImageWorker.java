package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageWorker {


	public static int[] findCoords(BufferedImage image) throws IOException, AWTException {
		Dimension screenSize  = Toolkit.getDefaultToolkit().getScreenSize();
		Robot robot = new Robot();
		int x= 0 ;
		int y= 0;
		BufferedImage screenCapture =
				robot.createScreenCapture(new Rectangle(0, 0, screenSize.width, screenSize.height));
		ImageIO.write(screenCapture,"png",new File("XD.png"));
		BufferedImage image1;
		do {
			image1 = screenCapture.getSubimage(x++,y,image.getWidth(),image.getHeight());
//			System.out.println(x + " <-x y-> " + y);
			if(x == screenSize.width - image.getWidth()) {
				x = 0;
				if(y<screenSize.height-image.getHeight())
					y++;
			}
		}while (!compareImages(image,image1));
		System.out.println("Coords found");
		System.out.println(compareImages(image, image1));
		return new int[]{x-1,y-1};
	}
	public static boolean compareImages(BufferedImage image,BufferedImage image1){
		if(!(image.getWidth()==image1.getWidth() && image.getHeight() == image1.getHeight()))
			return false;

		for(int op = 0 ; op < image.getHeight() ; ++ op){
			for(int po = 0 ; po < image.getWidth() ; ++ po){
				if(image.getRGB(po,op) != image1.getRGB(po,op))
					return false;
			}
		}
		return true;
	}
	public static byte[] parseImageToByteArray(BufferedImage image,String format) throws IOException {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ImageIO.write(image,format,byteArray);
		return byteArray.toByteArray();
	}


}
