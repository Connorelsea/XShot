package com.elsealabs.xshot.graphics;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import com.elsealabs.xshot.error.ErrorManager;

/**
 * XImage.java
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
	
	public boolean writeImage(File file, FORMAT format)
	{
		try
		{
			ImageIO.write(image, format.toString(), file);
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

	public int getWidth()
	{
		return image.getWidth();
	}

	public int getHeight()
	{
		return image.getHeight();
	}

	public BufferedImage getBufferedImage()
	{
		return image;
	}

}