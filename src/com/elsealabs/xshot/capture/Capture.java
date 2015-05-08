package com.elsealabs.xshot.capture;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Capture.java
 * 
 * A representation of a Capture. It holds the image of the
 * entire screen in addition to the original  user-selected
 * bounds and the updated bounds. This allows  the  capture
 * to be resized past its original dimensions.
 *
 */
public class Capture
{
	private BufferedImage image;
	private Rectangle     original;
	private Rectangle     updated;
	
	public Capture(BufferedImage image, Rectangle originalBounds)
	{
		this.image    = image;
		this.original = originalBounds;
		this.updated  = originalBounds;
	}
	
	public BufferedImage getCurrentImage()
	{
		return getSubImage(updated);
	}
	
	public BufferedImage getOriginalImage()
	{
		return getSubImage(original);
	}
	
	public BufferedImage getSubImage(Rectangle d)
	{
		return image.getSubimage(d.x, d.y, d.width, d.height);
	}

}