package com.elsealabs.xshot.annotation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class AnnotationMouseMotionListener implements MouseMotionListener
{

	@Override
	public void mouseDragged(MouseEvent e)
	{
		
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		System.out.println("Moving");
	}
	
}