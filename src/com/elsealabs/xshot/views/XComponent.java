package com.elsealabs.xshot.views;

import java.awt.Graphics2D;

import com.elsealabs.xshot.property.XProperty;
import com.elsealabs.xshot.property.XPropertySet;

public abstract class XComponent
{
	private XPropertySet properties;
	
	public XComponent()
	{
		properties = new XPropertySet();
	}
	
	public abstract void paint(Graphics2D g);
	
	public void addProperty(XProperty property)
	{
		properties.addProperty(property);
	}
	
	public XProperty getProperty(String name)
	{
		return properties.getProperty(name);
	}
}