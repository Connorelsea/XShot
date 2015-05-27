package com.elsealabs.xshot.component;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public interface Component
{
	public void mouseDragged  (MouseEvent e);
	public void mousePressed  (MouseEvent e);
	public void mouseReleased (MouseEvent e);
	
	public void paint(Graphics2D g);
}