package model;

import java.awt.*;

public class SpecialSpot {

	private String name;
	private Rectangle hitBox;

	public SpecialSpot(String name, Rectangle hitBox) {
		this.name = name;
		this.hitBox = hitBox;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}
}
