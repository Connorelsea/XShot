package com.elsealabs.xshot.component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

public class ComponentZoom {

	private Stroke foregroundStroke;
	private Color  foregroundColor;
	private Shape  shape;

	public static ComponentZoom DEFAULT_MODERN;
	public static ComponentZoom DEFAULT_SNIPPINGTOOL;
	public static ComponentZoom DEFAULT_SMOOTH;

	public ComponentZoom(
		Stroke foregroundStroke,
		Color  foregroundColor,
		Shape  shape
	) {
		this.foregroundStroke = foregroundStroke;
		this.foregroundColor  = foregroundColor;
		this.shape            = shape;
	}

	public void paint(Graphics2D g, BufferedImage image)
	{

	}

}