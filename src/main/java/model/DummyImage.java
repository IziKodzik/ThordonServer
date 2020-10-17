package model;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.*;
import java.util.List;

public class DummyImage {

	private final Map<String, List<SpecialSpot>> specialSpots = new LinkedHashMap<>();
//	private Map<String,Ac>


	private BufferedImage image;

	public DummyImage(BufferedImage image) {
		this.image = image;
		specialSpots.put("sensitive",new ArrayList<>());
	}

	public void addSpot(SpecialSpot spot){

		specialSpots.get(spot.getName()).add(spot);
	}
	public void addSensitiveSpot(Rectangle rectangle){
		this.addSpot(new SpecialSpot("sensitive",rectangle));
	}

	public List<SpecialSpot> getSensitiveSpots(){
		return specialSpots.get("sensitive");
	}

	public BufferedImage getImage() {
		return image;
	}



	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
