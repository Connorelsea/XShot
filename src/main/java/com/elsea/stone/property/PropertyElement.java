package com.elsea.stone.property;

import org.w3c.dom.*;

/**
 * PropertyElement.java
 * 
 * An abstract representation of a data container that can either contain
 * other data containers or property data.
 * 
 * @author Connor M. Elsea
 */
public abstract class PropertyElement
{
	private String name;
	private String type;
	private boolean empty;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		if (type == null) return " ";
		return type;
	}
	
	public boolean isEmpty()
	{
		return empty;
	}
	
	public void setEmpty(boolean empty)
	{
		this.empty = empty;
	}
	
	public abstract void print(int level);
	
	public void print()
	{
		print(0);
	}

	public abstract void write(Document doc, Element parent);
	
}