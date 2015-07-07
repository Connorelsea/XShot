package com.elsea.stone.property;

import org.w3c.dom.*;


/**
 * Property.java
 * 
 * A Property is a container holding information about a  property  such as
 * its name, current value, default value.
 * 
 * @author Connor M. Elsea
 */
public class Property extends PropertyElement
{
	private String currentValue;
	private String defaultValue;
	
	public Property()
	{
		setEmpty(true);
	}
	
	public void setCurrentValue(String currentValue)
	{
		this.currentValue = currentValue;
	}
	
	public String getCurrentValue()
	{
		return currentValue;
	}
	
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}
	
	public String getDefaultValue()
	{
		return defaultValue;
	}
	
	
	public void print(int level)
	{
		for (int i = 0; i < level; i++) System.out.print("|     ");
		System.out.println("> (" + getName() + " -> " + currentValue + ") :: " + getType());
	}

	@Override
	public void write(Document doc, Element parent)
	{
		Element prop        = doc.createElement(getName());
		Element currentProp = doc.createElement("current");
		Element defaultProp = doc.createElement("default");
		
		// Create child nodes for values
		currentProp.appendChild(doc.createTextNode(currentValue));
		defaultProp.appendChild(doc.createTextNode(defaultValue));
		
		// Append child nodes to parent property node
		prop.appendChild(currentProp);
		prop.appendChild(defaultProp);
		
		// Set group attribute of parent property node to false
		Attr attr = doc.createAttribute("group");
		attr.setValue("false");
		prop.setAttributeNode(attr);
		
		// Set type attribute
		Attr attrType = doc.createAttribute("type");
		attrType.setValue(getType());
		prop.setAttributeNode(attrType);

		// Append the newly created node to its specified parent
		parent.appendChild(prop);
	}
	
}