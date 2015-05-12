package com.elsealabs.xshot.views;

import javax.swing.*;
import java.awt.*;

public class ModernLabel extends JLabel
{
	private static final long serialVersionUID = 1L;

	private String text;
	private Font   font;
	private Color  color;

	public ModernLabel(String text, Color color, Font font)
	{
		this.text = text;
		this.color = color;
		this.font = font;

		setHorizontalAlignment(JLabel.CENTER);
		setText(text);
		setForeground(color);
		if (font != null) setFont(font);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D gd = (Graphics2D) g;
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g);
	}
}
