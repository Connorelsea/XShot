package com.elsealabs.xshot;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class Image {
	
	private BufferedImage image;
	
	public static enum FORMAT {
		JPEG, PNG, GIF
	}
	
	public Image(BufferedImage image) {
		this.image = image;
	}
	
	public boolean writeImage(Path path, FORMAT format) {
		
		try {
			
			ImageIO.write(image, format.toString(), path.toFile());
			return true;
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			return false;
			
		}
		
	}

}