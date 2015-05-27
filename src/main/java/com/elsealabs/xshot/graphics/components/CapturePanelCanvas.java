package com.elsealabs.xshot.graphics.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.elsealabs.xshot.math.Scale;

public class CapturePanelCanvas extends JPanel
{
	private Dimension size;
	private Scale     scale;
	
	public void build()
	{
		setBackground(Color.white);
		setLayout(null);
		setSize(size);
	}
	
	public void supplyCapturePanel()
	{
		
	}
	
	public Scale getScale()
	{
		return scale;
	}
	
	public void setScale(Scale scale)
	{
		this.scale = scale;
	}
}