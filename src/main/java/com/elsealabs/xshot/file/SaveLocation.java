package com.elsealabs.xshot.file;

import com.elsealabs.xshot.program.Program;

public class SaveLocation {
	
	private String name;
	private String path;
	private boolean isDefault = false;
	
	private Program program = Program.getInstance();
	private SaveLocationPool pool = program.getSaveLocationPool();
	
	public SaveLocation(String name, String path)
	{
		this.name = name;
		this.path = path;
	}
	
	public void remove()
	{
		pool.removeLocation(this);
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