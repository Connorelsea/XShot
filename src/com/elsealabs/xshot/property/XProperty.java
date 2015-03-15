package com.elsealabs.xshot.property;

import com.elsealabs.xshot.views.XComponent;

public class XProperty
{
	private String     name;
	private String     description;
	
	private String valueCurrent;
	private String valuePrevious;
	private String valueDefault;
	
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
	
	public void update(String value)
	{
		valuePrevious = valueCurrent;
		valueCurrent = value;
	}

	public String getValueCurrent()
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