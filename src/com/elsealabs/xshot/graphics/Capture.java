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

	public static final int NORTH = 1;
	public static final int SOUTH = 2;
	public static final int EAST  = 3;
	public static final int WEST  = 4;

	public Capture(XImage image, Rectangle originalBounds)
	{
		this.image = image;
		this.originalBounds = originalBounds;
		this.updatedBounds  = originalBounds;
	}

	public void addTo(int direction, int add)
	{
		if (direction == Capture.NORTH)
		{
			updatedBounds.setBounds(
					(int) updatedBounds.getX(),
					(int) updatedBounds.getY() - add,
					(int) updatedBounds.getWidth(),
					(int) updatedBounds.getHeight() + add
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

	public void updateBounds(Rectangle updatedBounds)
	{
		this.updatedBounds = updatedBounds;
	}

	public Rectangle getUpdatedBounds()
	{
		return updatedBounds;
	}

	public Rectangle getOriginalBounds()
	{
		return originalBounds;
	}

}