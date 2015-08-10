package com.elsea.stone.property;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PropertyPoolSearch {
	
	private PropertyPool pool;
	private ArrayList<PropertyElement> valuePool;
	
	public PropertyPoolSearch(PropertyPool pool)
	{
		this.pool = pool;
	}
	
	/**
	 * Return the first Property Element with the name specified by the argument.
	 * The returned property element could be a group or a property.
	 * 
	 * @param elementName The name of the element you want to find
	 * @return The element with the specified name
	 */
	public PropertyElement getElement(String elementName)
	{
		List<PropertyElement> results = getAllElements(elementName);
		if (results.size() > 0) return results.get(0);
		else return null;
	}
	
	/**
	 * Returns all element, whether property or group, with the specified name.
	 * 
	 * @param name The name of the elements you want to find
	 * @return The elements with the specified name
	 */
	public List<PropertyElement> getAllElements(String name)
	{
		return filter(p -> p.getName().equals(name));
	}
	
	/**
	 * Returns the first group with the name specified by the argument.
	 * 
	 * @param name The name of the group you want to find
	 * @return The group with the specified name
	 */
	public PropertyGroup getGroup(String name)
	{
		for (PropertyElement p : getAllElements(name)) {
			if (p instanceof PropertyGroup) return (PropertyGroup) p;
		}
		return null;
	}
	
	/**
	 * Returns the first property with the name specified by the argument.
	 * 
	 * @param name The name of the property you want to find
	 * @return The property with the specified name
	 */
	public Property getProperty(String name)
	{
		for (PropertyElement p : getAllElements(name)) {
			if (p instanceof Property) return (Property) p;
		}
		return null;
	}
	
	/**
	 * Tests whether or not the pool being searched has duplicate  elements
	 * that each have the same name. It does not test if they have the same
	 * values.
	 * 
	 * @param name The name to identify any duplicates of
	 * @return Whether or not there are duplicates
	 */
	public boolean hasDuplicatesOf(String name)
	{
		if (filter(p -> p.getName().equals(name)).size() > 1) return true;
		else return false;
	}
	
	/**
	 * Return a list containing all children of the first group found with the name
	 * specified by the argument of this method.
	 * 
	 * @param groupName The name of the group whose children will be found
	 * @return The children of the specified group
	 */
	public List<PropertyElement> getElementsInGroup(String groupName)
	{
		List<PropertyElement> results =
			filter(p -> p.getName().equals(groupName) && p instanceof PropertyGroup);
		
		if (results.size() > 0)
		{
			PropertyGroup g = ((PropertyGroup) results.get(0));
			return g.getChildren();
		}
		else return null;
	}
	
	/**
	 * Tests each element in the pool against a predicate
	 * 
	 * @param p The predicate to test each element against
	 * @return A list of the elements that tested true against the predicate
	 */
	public ArrayList<PropertyElement> filter(Predicate<PropertyElement> p)
	{
		valuePool = new ArrayList<PropertyElement>();
		recursive_filter(p, pool.getParent());
		return valuePool;
	}
	
	private void recursive_filter(Predicate<PropertyElement> p, PropertyElement current)
	{
		if (p.test(current))
			valuePool.add(current);
		
		if (current instanceof PropertyGroup)
		{
			PropertyGroup group = (PropertyGroup) current;
			
			for (int i = 0; i < group.getSize(); i++)
				recursive_filter(p, group.getChildAt(i));
		}
	}
	
	public void forEach(Consumer<PropertyElement> c)
	{
		recursive_forEach(c, pool.getParent());
	}
	
	private void recursive_forEach(Consumer<PropertyElement> c, PropertyElement current)
	{
		c.accept(current);
		
		if (current instanceof PropertyGroup)
		{
			PropertyGroup group = (PropertyGroup) current;
			
			for (int i = 0; i < group.getSize(); i++)
				recursive_forEach(c, group.getChildAt(i));
		}
	}

}