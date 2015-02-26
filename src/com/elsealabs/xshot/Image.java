package com.elsealabs.xshot;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class Image {
	
	private BufferedImage image;
	
	public static enum FORMAT
	{
		JPEG, PNG, GIF
	}
	
	public Image(BufferedImage image)
	{
		this.image = image;
	}
	
	public boolean writeImage(Path path, FORMAT format)
	{
		try 
		{
			ImageIO.write(image, format.toString(), path.toFile());
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}
	
	public Image getSubImage(Rectangle rec)
	{
		BufferedImage bimg = null;
		
		try
		{
			bimg = getBufferedImage().getSubimage(rec.x, rec.y, rec.width, rec.height);
		}
		catch (Exception ex)
		{
			ErrorManager.getInstance().newError(
				new Error(ex, "Problem getting sub image!")
			);
		}
		
		return new Image(bimg);
	}

	public BufferedImage getBufferedImage()
	{
		return image;
	}
}