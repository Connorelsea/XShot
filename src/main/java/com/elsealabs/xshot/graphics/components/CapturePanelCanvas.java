package com.elsealabs.xshot.graphics.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class CapturePanelCanvas extends JPanel
{
	private Dimension size;
	
	public void build()
	{
		setBackground(Color.white);
		setLayout(null);
		setSize(size);
	}
	
	public void supplyCapturePanel()
	{
		
	}
}