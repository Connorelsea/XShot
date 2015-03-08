package com.elsealabs.xshot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

public class ComponentSelection {
	
	private XRectangle shape;
	private XImage     image;
	
	private boolean decorated;
	private Shape   decorShape;
	private Color   decorForeground;
	private Color   decorBackground;
	
	private Stroke  strokeForeground;
	private Color   strokeForegroundColor;
	private Stroke  strokeBackground;
	private Color   strokeBackgroundColor;
	
	/**
	 * 
	 * @param rectangle The size of the component selection
	 * @param image The image to display in the selection
	 * @param decorShape The shape of the selection decorations
	 * @param decorForeground The foreground/border color of the selection decorations
	 * @param decorBackground The background color of the selection decorations
	 * @param strokeForeground The front-most stroke
	 * @param strokeForegroundColor The front-most stroke's color
	 * @param strokeBackground The back-most stroke
	 * @param strokeBackgorundColor The back-most stroke's color
	 */
	public ComponentSelection(
		XRectangle rectangle,
		XImage image,
		Shape  decorShape,
		Color  decorForeground,
		Color  decorBackground,
		Stroke strokeForeground,
		Color  strokeForegroundColor,
		Stroke strokeBackground,
		Color  strokeBackgorundColor
	) {
		this.shape = rectangle;
		this.image = image;
		
		this.decorated = true;
		this.decorShape = decorShape;
		this.decorBackground = decorBackground;
		this.decorForeground = decorForeground;
		
		this.strokeForeground = strokeForeground;
		this.strokeForegroundColor = strokeForegroundColor;
		this.strokeBackground = strokeBackground;
		this.strokeBackgroundColor = strokeBackgorundColor;
	}
	
	public void paint(Graphics2D g)
	{
		// TODO: make compenent selection self-painting
	}

}