package com.elsealabs.xshot.resource;

import com.elsealabs.xshot.program.Program;

import java.nio.file.Path;

public abstract class Resource<T>
{
	private T       item;
	private String  name;
	private Path    path;
	private boolean broken;

	public Resource(String name, Path path)
	{
		this.name = name;
		this.path = path;
	}

	public boolean retrieve()
	{
		if (Program.getInstance().getBuildType() == Program.BUILD_TYPE.JAR)
		{
			return retrieveJAR();
		}
		else
		{
			return retrieveIDE();
		}
	}

	public abstract boolean retrieveIDE();

	public abstract boolean retrieveJAR();

	public String getName()
	{
		return name;
	}

	public T getItem()
	{
		return item;
	}

	public void setItem(T t)
	{
		item = t;
	}

	public void setBroken(boolean broken)
	{
		this.broken = broken;
	}

	public boolean isBroken()
	{
		return broken;
	}

	public Path getPath()
	{
		return path;
	}
}