package com.elsealabs.xshot.annotation;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public abstract class Annotation
{
	private float scale;
	
	public abstract void mouseMoved(MouseEvent e);
	public abstract void mouseClicked(MouseEvent e);
	public abstract void mouseReleased(MouseEvent e);
	
	public abstract void paint(Graphics g);
}