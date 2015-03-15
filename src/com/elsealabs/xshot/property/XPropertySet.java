package com.elsealabs.xshot.property;

import java.util.ArrayList;

/**
 * XProperty.java
 * 
 * A data structure containing properties. An object typically
 * has multiple properties and will typically have an instance
 * of this class where XProperties are stored  and  retrieved.
 * 
 * @author Connor Elsea
 * @version 1.0
 */
public class XPropertySet
{
	private ArrayList<XProperty> properties;
	
	public XPropertySet()
	{
		properties = new ArrayList<XProperty>();
	}
	
	/**
	 * Return the instance of the property whose name is specified
	 * 
	 * @param name The name of the property that will be  returned
	 * @return The instance of the property whose name is supplied
	 */
	public XProperty getProperty(String name)
	{
		return properties.stream()
			.filter(x -> x.getName().equals(name))
			.findFirst()
			.get();
	}
	
	/**
	 * Return the value of the property whose name is specified
	 * 
	 * @param propertyName The name of the property whose value
	 * 					   will be returned
	 * @return The value of the property whose name is supplied
	 */
	public String getValueOf(String propertyName)
	{
		return properties.stream()
				.filter(x -> x.getName().equals(propertyName))
				.findFirst()
				.get()
				.getValue();
	}
	
	public void addProperty(XProperty property)
	{
		properties.add(property);
	}
}