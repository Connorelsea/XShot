package com.elsealabs.xshot.graphics;

import java.awt.*;

/**
 * Capture.java
 *
 * Holds the full possible capture, and the  bounds  which the user
 * specified. Once in ViewPicture, the user can resize  the picture
 * such that it includes parts of the image they did not originally
 * select.
 */
public class Capture
{
	private XImage image;
	private Rectangle originalBounds;
	private Rectangle updatedBounds;
	private Rectangle totalBounds;

	public static enum AREA
	{
		NORTH, SOUTH, EAST, WEST,
		NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST
	}

	public Capture(XImage image, Rectangle originalBounds)
	{
		this.image = image;
		this.originalBounds = originalBounds;
		this.updatedBounds  = originalBounds;
		
		this.totalBounds = new Rectangle(image.getWidth(), image.getHeight());
	}

	public void addTo(AREA area, int amountToAdd)
	{
		if (area == AREA.NORTH)
		{
			updatedBounds.setBounds(
					(int) updatedBounds.getX(),
					(int) updatedBounds.getY() - amountToAdd,
					(int) updatedBounds.getWidth(),
					(int) updatedBounds.getHeight() + amountToAdd
			);
		}
	}

	public XImage getFullImage()
	{
		return image;
	}

	public XImage getBoundedImage() {
		return image.getSubImage(updatedBounds);
	}

	public Rectangle getUpdatedBounds()
	{
		return updatedBounds;
	}

	public Rectangle getOriginalBounds()
	{
		return originalBounds;
	}

	public Rectangle getTotalBounds()
	{
		return totalBounds;
	}

}