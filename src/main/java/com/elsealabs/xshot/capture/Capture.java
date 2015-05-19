package com.elsealabs.xshot.capture;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

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

	public static enum AREA
	{
		NORTH, SOUTH, EAST, WEST, 
		NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST,
		WHOLE
	}

	public Capture(BufferedImage image, Rectangle total, Rectangle originalBounds)
	{
		this.image    = image;
		this.total    = total;
		this.original = originalBounds;
		this.updated  = originalBounds;
	}

	public void addTo(AREA area, int amountToAdd)
	{
		switch (area)
		{
		case NORTH:
			updated.setBounds(updated.x, updated.y - amountToAdd, updated.width, updated.height + amountToAdd);
			break;

		case SOUTH:
			updated.setBounds(updated.x, updated.y, updated.width, updated.height - amountToAdd);
			break;

		case EAST:
			updated.setBounds(updated.x, updated.y, updated.width - amountToAdd, updated.height);
			break;

		case WEST:
			updated.setBounds(updated.x - amountToAdd, updated.y, updated.width + amountToAdd, updated.height);
			break;

		default:
			break;
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