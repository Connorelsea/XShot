package com.elsealabs.xshot.property;

/**
 * XProperty.java
 * 
 * A representation of an alterable property of some  object,
 * intended to store information that could be edited at some
 * point.
 * 
 * @author Connor Elsea
 * @version 1.0
 */
public class XProperty
{
	/**
	 * Information such as the name and the  description will
	 * be displayed to  users  when  they  are editing  these
	 * properties in a user interface.
	 */
	private String name;
	private String description;
	
	/**
	 *  A small history of values is kept so that a component
	 *  supplied a faulty value can revert back to a  working
	 *  value.
	 */
	private String valueCurrent;
	private String valuePrevious;
	private String valueDefault;
	
	/**
	 * The default constructor, supplying all necessary values.
	 * 
	 * @param valueDefault The default value
	 * @param name The name of this property to be  used  when
	 *             referencing this property  in  get  methods.
	 * @param description The description of what this property
	 * 					  changes.
	 */
	public XProperty(
		String     valueDefault,
		String     name,
		String     description
	)
	{
		this.name         = name;
		this.description  = description;
		this.valueDefault = valueDefault;
		this.valueCurrent = valueDefault;
	}
	
	/**
	 * Changes the value of  the  property, and  stores  information
	 * about the previous property. Information about the property's
	 * previous value will be used  to  restore  a  component  to  a
	 * working state if the new updated value is incorrect.
	 * 
	 * @param value The value to change this property to
	 */
	public void update(String value)
	{
		valuePrevious = valueCurrent;
		valueCurrent = value;
	}

	public String getValue()
	{
		return valueCurrent;
	}
	
	public String getValueDefault()
	{
		return valueDefault;
	}
	
	public String getValuePrevious()
	{
		return valuePrevious;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}
}