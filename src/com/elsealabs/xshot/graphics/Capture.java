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

	public Capture(XImage image, Rectangle originalBounds)
	{
		this.image = image;
		this.originalBounds = originalBounds;
		this.updatedBounds  = originalBounds;
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