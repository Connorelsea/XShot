package com.elsealabs.xshot.graphics.util;



/**
 * ViewAbstract.java
 * 
 * An abstract representation of a parent-level display. When created, it is
 * added to the ViewManager, and when it is ended, it is  removed  from  the
 * ViewManager. This allows global access of needed data.
 * 
 * @author Connor Elsea (@Connorelsea)
 */
public abstract class ViewAbstract
{
	private String  name;
	
	public ViewAbstract(String name)
	{
		this.name = name;
	}
	
	/**
	 * Adds the view to the ViewManager and then builds the view.
	 */
	public void start()
	{
		ViewManager.getInstance().addView(this);
		build();
	}
	
	/**
	 * Removes the view from the view manager and closes the view.
	 */
	public void end()
	{
		ViewManager.getInstance().removeView(this);
		close();
	}
	
	/**
	 * Where the View will be created. For example, a JFrame may be created and filled with components
	 */
	public abstract void build();
	
	/**
	 * Defines how the view will be shown.
	 */
	public abstract void show();
	
	/**
	 * Defines how the view will be closed.
	 */
	public abstract void close();
	
	public String getName()
	{
		return name;
	}
}