package com.elsaelabs.xshot.theme;

import java.util.ArrayList;
import java.util.List;

public class Theme
{
	private List<ThemeElement<?>> elements;
	
	public Theme()
	{
		elements = new ArrayList<ThemeElement<?>>();
	}
	
	public void addElement(ThemeElement<?> e)
	{
		elements.add(e);
	}
	
	public void removeElement(ThemeElement<?> e)
	{
		elements.remove(e);
	}
	
	public List<ThemeElement<?>> getElements()
	{
		return elements;
	}
}