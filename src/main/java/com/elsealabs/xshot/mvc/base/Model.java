package com.elsealabs.xshot.mvc.base;

public abstract class Model {

	public void start()
	{
		init();
	}
	
	public void end()
	{
		deinit();
	}
	
	public abstract void init();
	public abstract void deinit();
	
}