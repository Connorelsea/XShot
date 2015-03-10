package com.elsealabs.xshot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

public class ComponenetSelection {
	
	private boolean   decorated;
	private Rectangle decorShape;
	private Color     decorForegroundColor;
	private Color     decorBackgroundColor;
	
	private Stroke  strokeForeground;
	private Color   strokeForegroundColor;
	private Stroke  strokeBackground;
	private Color   strokeBackgroundColor;
	
	private Stroke  resetStroke;
	
	public static ComponenetSelection DEFAULT_MODERN = new ComponenetSelection(
		new Rectangle(7, 7),      // Decoration Shape
		Color.WHITE,              // Decoration Foreground Color
		Color.BLACK,              // Decoration Background Color
		generateStroke(1.0f, 4f), // Foreground Stroke
		Color.BLACK,              // Foreground Stroke Color
		generateStroke(1.0f),     // Background Stroke
		Color.WHITE               // Background Stroke Color
	);
	public static ComponenetSelection DEFAULT_SNIPPINGTOOL;
	public static ComponenetSelection DEFAULT_SMOOTH;
	
	/**
	 * Create a Component Selection object with the properties described as arguments.
	 * 
	 * @param decorShape The shape of the selection decorations
	 * @param decorForeground The foreground/border color of the selection decorations
	 * @param decorBackground The background color of the selection decorations
	 * @param strokeForeground The front-most stroke
	 * @param strokeForegroundColor The front-most stroke's color
	 * @param strokeBackground The back-most stroke
	 * @param strokeBackgorundColor The back-most stroke's color
	 */
	public ComponenetSelection(
		Rectangle  decorShape,
		Color  decorForeground,
		Color  decorBackground,
		Stroke strokeForeground,
		Color  strokeForegroundColor,
		Stroke strokeBackground,
		Color  strokeBackgorundColor
	) {
		
		this.decorated       = true;
		this.decorShape      = decorShape;
		this.decorBackgroundColor = decorBackground;
		this.decorForegroundColor = decorForeground;
		
		this.strokeForeground      = strokeForeground;
		this.strokeForegroundColor = strokeForegroundColor;
		this.strokeBackground      = strokeBackground;
		this.strokeBackgroundColor = strokeBackgorundColor;
		
		this.resetStroke = generateStroke(1f);
	}
	
	public static Stroke generateStroke(float width, float dashWidth)
	{
		return new BasicStroke(
			width,
			BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_MITER,
			10.0f,
			new float[] { dashWidth },
			0.0f
		);
	}
	
	public static Stroke generateStroke(float width)
	{
		return new BasicStroke(width);
	}
	
	public void paint(Graphics2D g, XRectangle rec, XImage image)
	{
		// Draw image
		g.drawImage(image.getSubImage(rec).getBufferedImage(), rec.x, rec.y, null);
		
		// Draw background stroke
		g.setStroke(strokeBackground);
		g.setColor(strokeBackgroundColor);
		g.draw(rec);
		
		// Draw foreground stroke
		g.setStroke(strokeForeground);
		g.setColor(strokeForegroundColor);
		g.draw(rec);
		
		// Draw decorations
		if (decorated)
		{
			g.setStroke(resetStroke);
			
			rec.getPointsAsArray().stream()
			.forEach(a ->
			{
				g.setColor(decorBackgroundColor);
				g.fillRect(a.x - (decorShape.width / 2), a.y - (decorShape.height / 2), decorShape.width, decorShape.height);
				
				g.setColor(decorForegroundColor);
				g.drawRect(a.x - (decorShape.width / 2), a.y - (decorShape.height / 2), decorShape.width, decorShape.height);
			});
		}
	}

}