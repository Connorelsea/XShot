package com.elsealabs.xshot.mvc.base;

public abstract class Controller {
	
	private View view;
	private Model model;
	
	public abstract void init();
	
	public void linkView(View view)
	{
		this.view = view;
	}
	
	public void linkModel(Model model)
	{
		this.model = model;
	}
	
}