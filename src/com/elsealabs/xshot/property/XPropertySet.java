package com.elsealabs.xshot.property;

import java.util.ArrayList;

public class XPropertySet
{
	private ArrayList<XProperty> properties;
	
	public void addProperty(XProperty property)
	{
		properties.add(property);
	}
	
	public ArrayList<XProperty> getProperties()
	{
		return properties;
	}
}