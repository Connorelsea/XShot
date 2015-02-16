package com.elsealabs.xshot;

import java.awt.image.BufferedImage;

public class Screenshot {
	
	private String name;
	private BufferedImage image;
	
	public Screenshot(BufferedImage image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}