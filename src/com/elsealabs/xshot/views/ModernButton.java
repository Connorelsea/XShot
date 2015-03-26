package com.elsealabs.xshot.views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class ModernButton extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Color colorBackground;
	private Color colorHoverBackground;

	private ModernLabel modernLabel;

	private ActionListener action;
	private MouseListener mouseListener;
	
	private boolean hovering;
	
	public ModernButton(
			ModernLabel modernLabel,
			Color colorBackground,
			Color colorHoverBackground,
			ActionListener action)
	{
		this.colorBackground = colorBackground;
		this.colorHoverBackground = colorHoverBackground;
		this.modernLabel = modernLabel;
		this.action = action;

		_defineListeners();
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
				setBackground(colorBackground);
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				hovering = true;
				setBackground(colorHoverBackground);
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				action.actionPerformed(null);
			}
		};
	}
	
	public void build()
	{
		setLayout(new BorderLayout());
		setBackground(colorBackground);
		add(modernLabel);

		addMouseListener(mouseListener);
	}

}