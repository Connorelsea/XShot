package com.elsaelabs.xshot.theme;


public abstract class ThemeElement<T>
{
	private String key;
	private T value;
	
	public String getKey()
	{
		return key;
	}
	
	public T get()
	{
		return value;
	}
}