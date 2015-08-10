package com.elsealabs.xshot.file;

public class SaveLocation {
	
	private String name;
	private String path;
	private boolean isDefault = false;
	
	public SaveLocation(String name, String path)
	{
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public boolean isDefault()
	{
		return isDefault;
	}
	
	public void setDefault(boolean value)
	{
		isDefault = value;
	}

}