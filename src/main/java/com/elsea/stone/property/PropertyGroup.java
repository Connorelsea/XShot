package com.elsea.stone.property;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * PropertyGroup.java
 * 
 * A PropertyGroup is a container that can contain other PropertyGroups or
 * Property objects.
 * 
 * @author Connor M. Elsea
 */
public class PropertyGroup extends PropertyElement
{
	private List<PropertyElement> children;
	private PropertyGroup parent;
	
	public PropertyGroup()
	{
		children = new ArrayList<PropertyElement>();
		setEmpty(true);
	}
	
	public void setParent(PropertyGroup parent)
	{
		this.parent = parent;
	}
	
	public PropertyGroup getParent()
	{
		return parent;
	}
	
	public int getSize()
	{
		return children.size();
	}
	
	public void addChild(PropertyElement element)
	{
		children.add(element);
	}
	
	public void removeChild(PropertyElement element)
	{
		children.remove(element);
	}
	
	public PropertyElement getChildAt(int index)
	{
		return children.get(index);
	}
	
	public List<PropertyElement> getChildrenOfName(String name)
	{
		return children.stream()
			.filter(e -> e.getName().equals(name))
			.collect(Collectors.toList());
	}
	
	public PropertyElement getChildOfName(String name)
	{
		return null;
	}
	
	public boolean hasChildGroups()
	{
		if (children.size() > 0 || getChildGroups().size() > 0) return false;
		else return true;
	}
	
	public List<PropertyElement> getChildGroups()
	{
		return children.stream()
			.filter(e -> e instanceof PropertyGroup)
			.collect(Collectors.toList());
	}
	
	public List<PropertyElement> getChildProperties()
	{
		return children.stream()
			.filter(e -> e instanceof Property)
			.collect(Collectors.toList());
	}
	
	public List<PropertyElement> getChildren()
	{
		return children;
	}
	
	public void print(int level)
	{
		for (int i = 0; i < level; i++) {
			if (i < level - 1) System.out.print("|     ");
			else System.out.print("|     ");
		}
		System.out.println("Group: " + getName() + " :: " + getType());
		
		for (PropertyElement element : children)
		{
			element.print(level + 1);
		}
	}

	@Override
	public void write(Document doc, Element parent)
	{
		Element group = doc.createElement(getName());
		
		/**
		 * If this element has no parent and is a group, it is assumed that it is  the
		 * singular parent element for the entire group and is added as a child of the
		 * document itself. If this element's name is not "parent" then a  warning  is
		 * shown in the console.
		 */
		if (parent == null)
		{
			doc.appendChild(group);
			
			if (!getName().equals("parent"))
			{
				System.err.println("Warning: Second parameter null causes direct add to document. Only intended for parent group.");
			}
		}
		else
		{
			parent.appendChild(group);
		}
		
		// Set group attribute of parent group node to true
		Attr attr = doc.createAttribute("group");
		attr.setValue("true");
		group.setAttributeNode(attr);
		
		// Set group type attribute
		Attr attrType = doc.createAttribute("type");
		attrType.setValue(getType());
		group.setAttributeNode(attrType);
		
		// Write all children
		children.stream()
			.forEach(child -> child.write(doc, group));
	}
}