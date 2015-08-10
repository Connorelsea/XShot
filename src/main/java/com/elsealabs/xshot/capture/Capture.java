package com.elsealabs.xshot.capture;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;

import com.elsealabs.xshot.math.Scale;

/**
 * Capture.java
 * 
 * A representation of a Capture. It holds the image of the entire screen in
 * addition to the original user-selected bounds and the updated bounds. This
 * allows the capture to be resized past its original dimensions.
 * 
 */
public class Capture
{
	private BufferedImage image;
	private Rectangle total;
	private Rectangle original;
	private Rectangle updated;
	
	private Scale scale = Scale.getInstance();

	public static enum AREA
	{
		NORTH, SOUTH, EAST, WEST, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST, WHOLE
	}

	public Capture(BufferedImage image, Rectangle total,
			Rectangle originalBounds)
	{
		this.image = image;
		this.total = total;
		this.original = originalBounds;
		this.updated = originalBounds;
	}

	public void addTo(AREA area, int amountToAdd)
	{
		Rectangle updatedBounds = new Rectangle();
		int x = -1;
		int y = -1;
		int width = -1;
		int height = -1;
		
		switch (area)
		{
			case NORTH:
				x = updated.x;
				y = updated.y - amountToAdd;
				width = updated.width;
				height = updated.height + amountToAdd;
				break;
	
			case SOUTH:
				x = updated.x;
				y = updated.y;
				width = updated.width;
				height = updated.height - amountToAdd;
				break;
	
			case EAST:
				x = updated.x;
				y = updated.y;
				width = updated.width - amountToAdd;
				height = updated.height;
				break;
	
			case WEST:
				x = updated.x - amountToAdd;
				y = updated.y;
				width = updated.width + amountToAdd;
				height = updated.height;
				break;
	
			default:
				break;
		}
		
		// Ensure that the user does not drag the image out of bounds
		
		if ( (x < total.width  && x > 0) &&
			 (y < total.height && y > 0) &&
			 (width  + x < total.width  && width  > 0) &&
			 (height + y < total.height && height > 0))
		{
			updated.setBounds(x, y, width, height);
		}
	}

	/**
	 * Returns the original full image
	 * 
	 * @return The original full image
	 */
	public BufferedImage getTotalImage()
	{
		return image;
	}

	/**
	 * Returns the bounds of the full image, extending beyond the bounds of the
	 * original or the updated capture.
	 * 
	 * @return The full image bounds
	 */
	public Rectangle getTotalBounds()
	{
		return total;
	}

	/**
	 * Returns a sub-image of the whole image that adheres to the Capture's
	 * updated bounds.
	 * 
	 * @return Image with updated bounds
	 */
	public BufferedImage getUpdatedImage()
	{
		return getSubImage(updated);
	}

	/**
	 * Returns the most recent change in the capture's bounds
	 * 
	 * @return The updated capture bounds
	 */
	public Rectangle getUpdatedBounds()
	{
		return original;
	}

	/**
	 * Returns a sub-image of the whole image that adheres to the Capture's
	 * original bounds that were set upon creation.
	 * 
	 * @return Image with original bounds
	 */
	public BufferedImage getOriginalImage()
	{
		return getSubImage(original);
	}

	/**
	 * Returns the original capture bounds set upon creation
	 * 
	 * @return The original capture bounds
	 */
	public Rectangle getOriginalBounds()
	{
		return original;
	}

	public BufferedImage getSubImage(Rectangle d)
	{
		return image.getSubimage(d.x, d.y, d.width, d.height);
	}

}