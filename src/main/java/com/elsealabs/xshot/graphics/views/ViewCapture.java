package com.elsealabs.xshot.graphics.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.capture.CaptureDevice;
import com.elsealabs.xshot.capture.Monitor;
import com.elsealabs.xshot.graphics.components.CapturePanelCanvas;
import com.elsealabs.xshot.graphics.util.ViewAbstract;
import com.elsealabs.xshot.math.Scale;

/**
 * ViewCapture.java
 * 
 * Allows the user to view and alter a Capture object.
 * 
 * @author Connor Elsea (@Connorelsea)
 */
public class ViewCapture extends ViewAbstract
{
	/**
	 * Swing Related Objects
	 */
	private JFrame frame;
	
	private JPanel bar;
	private JPanel content;
	private CapturePanelCanvas container;
	
	private JScrollPane scrollPane;
	
	/**
	 * Graphics
	 */
	private Scale scale;
	
	/**
	 * Supplied Resources
	 */
	private Capture capture;
	
	/**
	 * State-Related Variables
	 */
	private boolean fullWindow;

	/**
	 * Call parent constructor to set the name of the View to be used in
	 * the View Manager.
	 * 
	 * @param name Name of the View
	 */
	public ViewCapture(String name)
	{
		super(name);
	}
	
	public void supplyCapture(Capture capture)
	{
		this.capture = capture;
	}

	@Override
	public void build()
	{
		frame.setSize(determineWindowSize());
		if (fullWindow) frame.setState(JFrame.MAXIMIZED_BOTH);
		
		bar = new JPanel();
		bar.setLayout(new FlowLayout());
		
		content = new JPanel();
		content.setLayout(new BorderLayout());
		
		scrollPane = new JScrollPane();
		content.add(scrollPane, BorderLayout.CENTER);
		
		container = new CapturePanelCanvas();
	}
	
	@Override
	public void show()
	{
		frame.setVisible(true);
	}

	@Override
	public void close()
	{
		frame.setVisible(false);
		frame.dispose();
	}
	
	/**
	 * Determine the window size based on the size of the supplied capture
	 * 
	 * @return A dimension representing the determined size of the window.
	 */
	public Dimension determineWindowSize()
	{
		CaptureDevice device = CaptureDevice.getInstance();
		Monitor monitor      = device.getDefaultMonitor();
		Dimension dimension  = new Dimension();
		
		if (monitor.getHeight() - 40 > capture.getUpdatedBounds().getHeight() ||
			monitor.getWidth()  - 40 > capture.getUpdatedBounds().getWidth())
		{
			dimension  = new Dimension(monitor.getWidth(), monitor.getHeight());
			fullWindow = true;
		}
		else
		{
			dimension  = new Dimension(capture.getUpdatedBounds().width, capture.getUpdatedBounds().height);
			fullWindow = false;
		}
		
		return dimension;
	}
	
}