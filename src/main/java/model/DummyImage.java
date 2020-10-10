package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.*;

public class DummyImage {

	private Map<String,SpecialSpot> specialSpots = new LinkedHashMap<>();
//	private Map<String,Ac>


	private BufferedImage image;

	public DummyImage(BufferedImage image) {
		this.image = image;
	}

	public void addSpot(SpecialSpot spot){
		this.specialSpots.putIfAbsent(spot.getName(),spot);
	}
	public SpecialSpot getSpot(String spotName){
		return this.specialSpots.get(spotName);
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
