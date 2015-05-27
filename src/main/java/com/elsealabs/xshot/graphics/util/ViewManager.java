package com.elsealabs.xshot.graphics.util;

import java.util.ArrayList;
import java.util.List;

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