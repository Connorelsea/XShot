package com.elsealabs.xshot.graphics.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewManager.java
 * 
 * The View Manager is a singleton object that contains all of the views. If needed
 * in the future, Views will be able to easily access information about each  other.
 * 
 * Only views that are currently in use or are currently being displayed are held in
 * the View Manager. Once a view is done being used or done being  displayed  it  is
 * removed from the View Manager.
 * 
 * @author Connor Elsea (@Connorelsea)
 */
public class ViewManager
{
	private static ViewManager instance;
	private List<ViewAbstract> views;
	
	public static ViewManager getInstance()
	{
		if (instance == null) instance = new ViewManager();
		return instance;
	}
	
	private ViewManager()
	{
		views = new ArrayList<ViewAbstract>();
	}
	
	public void addView(ViewAbstract va)
	{
		views.add(va);
	}
	
	public void removeView(ViewAbstract va)
	{
		views.remove(va);
	}
}