package com.elsealabs.xshot.views;

import java.awt.Graphics2D;

import com.elsealabs.xshot.property.XProperty;
import com.elsealabs.xshot.property.XPropertySet;

public abstract class XComponent
{
	private XPropertySet properties;
	
	public abstract void paint(Graphics2D g);
	
	public void addProperty(XProperty property)
	{
		properties.addProperty(property);
	}
}