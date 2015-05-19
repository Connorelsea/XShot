package com.elsealabs.xshot.math;

import java.awt.event.ActionListener;

public class Scale
{
	private float scale;
	private ActionListener scaleActions;
	
	public Scale()
	{
		this.scale = 1f;
	}
	
	public void setScaleActions(ActionListener scaleActions)
	{
		this.scaleActions = scaleActions;
	}
	
	public void updateScale()
	{
		scaleActions.actionPerformed(null);
	}
	
	public Scale(float scale)
	{
		this.scale = scale;
	}
	
	public void setScale(float scale)
	{
		this.scale = scale;
		updateScale();
	}
	
	public float getScale()
	{
		return scale;
	}
}