package com.elsealabs.xshot.property;

import java.util.ArrayList;

public class XPropertySet
{
	private ArrayList<XProperty> properties;
	
	public XPropertySet()
	{
		properties = new ArrayList<XProperty>();
	}
	
	public void addProperty(XProperty property)
	{
		properties.add(property);
	}
	
	public XProperty getProperty(String name)
	{
		return properties.stream()
			.filter(x -> x.getName().equals(name))
			.findFirst()
			.get();
	}
	
	public ArrayList<XProperty> getProperties()
	{
		return properties;
	}
}