package com.elsealabs.xshot.graphics;

public class XImagePosition
{
	
	private XImage image;
	private IntDimension dimension;
	
	public XImagePosition(XImage image, IntDimension dimension)
	{
		this.image = image;
		this.dimension = dimension;
	}
	
	public XImage getImage()
	{
		return image;
	}
	
	public IntDimension getDimension()
	{
		return dimension;
	}
	
	public void setDimension(IntDimension dimension)
	{
		this.dimension = dimension;
	}

}