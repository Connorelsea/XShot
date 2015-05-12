package com.elsealabs.xshot.resource;

import java.nio.file.Path;


public class ResourceManager
{
	private ResourceManager instance;
	
	private ResourceManager()
	{
		
	}
	
	public boolean loadFromInternal(Path path)
	{
		return true;
	}
	
	public boolean loadFromExternal(Path path)
	{
		return true;
	}
	
	public ResourceManager getInstance()
	{
		if (instance == null) instance = new ResourceManager();
		return instance;
	}
	
}