package com.elsealabs.xshot;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

import javax.imageio.ImageIO;

/**
 * Image.java
 * 
 * Implementation of an Image that is simplified for use
 * throughout the project
 * 
 * @author Connor Elsea
 * @version 1.0
 */
public class XImage {
	
	private BufferedImage image; 
	
	public static enum FORMAT
	{
		JPEG, PNG, GIF
	}
	
	public XImage(BufferedImage image)
	{
		// TODO: Make Image EXTEND BufferedImage!!!!
		this.image = image;
	}
	
	public void draw(Graphics g, int x, int y)
	{
		g.drawImage(getBufferedImage(), x, y, null);
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
			ErrorManager.getInstance().newError(
				ex, "Problem saving image to " + format, true
			);
			return false;
		}
		
	}
	
	public XImage getSubImage(Rectangle rec)
	{
		BufferedImage bimg = null;
		
		try
		{
			bimg = getBufferedImage().getSubimage(rec.x, rec.y, rec.width, rec.height);
		}
		catch (Exception ex)
		{
			ErrorManager.getInstance().newError(
				ex, "Problem getting sub image!", false
			);
		}
		
		return new XImage(bimg);
	}

	public BufferedImage getBufferedImage()
	{
		return image;
	}
}