package com.elsealabs.xshot.views;

import java.awt.Graphics2D;
import java.awt.Stroke;

import com.elsealabs.xshot.property.XProperty;

public class ViewCaptureFreeform extends View
{
	private XComponent image;
	private XComponent selection;
	private XComponent cursorZoom;
	
	public ViewCaptureFreeform()
	{
		defineComponents();
	}
	
	private void defineComponents()
	{
		image = new XComponent()
		{
			@Override
			public void paint(Graphics2D g)
			{
				
			}
		};
		
		image.addProperty(new XProperty(".5", "opacity", "The transparency of the image overlay"));
		image.addProperty(new XProperty("black", "color", "The color of the image overlay"));
	}
}