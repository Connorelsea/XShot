package com.elsea.stone.property;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PropertyPoolSearch {
	
	private PropertyPool pool;
	private ArrayList<PropertyElement> valuePool;
	
	public PropertyPoolSearch(PropertyPool pool)
	{
		this.pool = pool;
	}
	
	public void getElementsInGroup(String groupName)
	{
		
	}
	
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