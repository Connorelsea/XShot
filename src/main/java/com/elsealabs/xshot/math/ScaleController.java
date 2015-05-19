package com.elsealabs.xshot.math;


/**
 * ScaleController.java
 * 
 * Singleton object representing a scale controller. The scale controller can be accessed
 * throughout the entire program due to the fact that many objects  will  need  to  scale
 * their various attributes.
 * 
 * @author Connor Elsea (@Connorelsea)
 */
public class ScaleController
{
	private static ScaleController instance;
	private float scale;
	
	private ScaleController() { }
	
	public static ScaleController getInstance()
	{
		if (instance == null) instance = new ScaleController();
		return instance;
	}
	
	public float scale(float i)
	{
		return scale * i;
	}
	
	public double scale(double i)
	{
		return scale * i;
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