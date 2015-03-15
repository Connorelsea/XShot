package com.elsealabs.xshot.views;

import java.awt.Graphics2D;

import com.elsealabs.xshot.property.XProperty;
import com.elsealabs.xshot.property.XPropertySet;

/**
 * XComponent.java
 * 
 * A graphical component with alterable visual properties.
 * 
 * @author Connor Elsea
 * @version 1.0
 */
public abstract class XComponent
{
	/**
	 * A component is themeable, and accomplishes this functionality
	 * through the use of user-editable properties.
	 */
	private XPropertySet properties;
	
	public XComponent()
	{
		properties = new XPropertySet();
	}
	
	/**
	 * Defines how to draw this component.
	 * 
	 * @param g The graphics 2D object of the panel/frame this component
	 * is being drawn on
	 */
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