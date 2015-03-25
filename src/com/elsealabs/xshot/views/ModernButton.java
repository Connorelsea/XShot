package com.elsealabs.xshot.views;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class ModernButton extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private String text;
	
	private Color colorBackground;
	private Color colorForeground;
	private Color colorHover;
	private Color colorClick;
	
	private MouseListener mouseListener;
	
	private boolean hovering;
	
	public ModernButton(String text)
	{
		this.text = text;
		build();
	}
	
	private void _defineListeners()
	{
		mouseListener = new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				hovering = false;
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				hovering = true;
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
			}
		};
	}
	
	public void build()
	{
		addMouseListener(mouseListener);
	}

	public String getText()
	{
		return text;
	}


	public void setText(String text)
	{
		this.text = text;
	}


	public Color getColorBackground()
	{
		return colorBackground;
	}


	public void setColorBackground(Color colorBackground)
	{
		this.colorBackground = colorBackground;
	}


	public Color getColorForeground()
	{
		return colorForeground;
	}


	public void setColorForeground(Color colorForeground)
	{
		this.colorForeground = colorForeground;
	}


	public Color getColorHover()
	{
		return colorHover;
	}


	public void setColorHover(Color colorHover)
	{
		this.colorHover = colorHover;
	}


	public Color getColorClick()
	{
		return colorClick;
	}


	public void setColorClick(Color colorClick)
	{
		this.colorClick = colorClick;
	}
	
	

}