package com.elsealabs.xshot.math;

import java.awt.Point;
import java.awt.Rectangle;

public class Scale
{
	private static Scale instance;
	private float scale = 1f;
	
	public static Scale getInstance()
	{
		if (instance == null) instance = new Scale();
		return instance;
	}
	
	public float scale(float f) {
		return f * scale;
	}
	
	public int scale(int i) {
		return (int) ((float) i * (float) scale);
	}
	
	public Point scalePoint(Point p)
	{
		return new Point(scale(p.x), scale(p.y));
	}
	
	public Rectangle scaleRectangle(Rectangle r) {
		return new Rectangle(scale(r.x), scale(r.y), scale(r.width), scale(r.height));
	}
	
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	
	public float getScale()
	{
		return scale;
	}
	
}