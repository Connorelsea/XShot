package com.elsealabs.xshot.views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.UTFDataFormatException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import com.elsealabs.xshot.capture.Capture;

public class PanelCaptureNew extends JPanel
{
	private JComponent parent;
	private JFrame     frame;
	
	private int width;
	private int height;
	private Dimension frameSize;
	private Point viewportPoint;
	
	private Capture capture;
	
	public PanelCaptureNew(JComponent parent, JFrame frame, Capture capture)
	{
		this.parent  = parent;
		this.frame   = frame;
		this.capture = capture;
		
		this.width   = capture.getTotalBounds().width;
		this.height  = capture.getTotalBounds().height;
		
		this.frameSize = frame.getSize();
	}
	
	private void _positionViewport()
	{
		if (parent instanceof JScrollPane)
		{
			JViewport viewport = ((JScrollPane) parent).getViewport();
			
			int proper_width  = (viewport.getWidth()  / 2) - (capture.getUpdatedBounds().width  / 2);
			int proper_height = (viewport.getHeight() / 2) - (capture.getUpdatedBounds().height / 2);
			
			Point point = new Point(
					(500 / 2) + capture.getUpdatedBounds().x - proper_width,
					(500 / 2) + capture.getUpdatedBounds().y - proper_height
			);
			
			viewport.setViewPosition(point);
		}
	}
	
	public void paint(Graphics gd)
	{
		Graphics2D g = (Graphics2D) gd;
		
		g.clearRect(0, 0, width, height);
		
		_positionViewport();
		
		g.drawImage(
				capture.getUpdatedImage(),
				capture.getUpdatedBounds().x,
				capture.getUpdatedBounds().y,
				null
		);
	}

}