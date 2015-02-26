package com.elsealabs.xshot;

import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;

/**
 * Capturer.java
 * 
 * An Object that knows how to capture images of multiple monitors
 * 
 * @author Connor Elsea
 * @version 1.0
 */
public class Capturer {
	
	private GraphicsEnvironment graphics;
	private ArrayList<Monitor> monitors;
	private JFrame frame;
	
	private static Capturer instance;
	
	private Capturer(JFrame attachedFrame) {
		this.frame = attachedFrame;
		_initDevices();
	}
	
	public static Capturer getInstance(JFrame frame) {
		if (instance == null) {
			return (instance = new Capturer(frame));
		} else {
			return instance;
		}
	}
	
	public static Capturer getInstance() {
		return instance;
	}
	
	/**
	 * _initDevices()
	 * 
	 * Finds and stores all display devices this computer is using.
	 * 
	 * @version 1.0
	 */
	private void _initDevices() {
		
		graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
		monitors = new ArrayList<Monitor>();
		
		ArrayList<GraphicsDevice> devices = new ArrayList<GraphicsDevice>(
			Arrays.asList(graphics.getScreenDevices())
		);
		
		devices.stream().forEach(a -> monitors.add(new Monitor(a)));
	}
	
	/**
	 * findCurrentMonitor()
	 * 
	 * Determines which graphics device is currently displaying  this
	 * program. The collection of Monitors is then updated to reflect
	 * this new data.
	 * 
	 * @version 1.0
	 */
	public void findCurrentMonitor() {
		
		GraphicsDevice current = frame.getGraphicsConfiguration().getDevice();
		monitors.stream().forEach(a -> a.setCurrent(a.getDevice() == current));
		
	}
	
	/**
	 * getCurrentMonitor()
	 * 
	 * Fires findCurrentMonitor() to ensure that the current monitor is
	 * updated, and then finds the current  monitor  in  the  array  of
	 * monitors and returns it.
	 * 
	 * @version 1.0
	 */
	public Monitor getCurrentMonitor() {
		
		findCurrentMonitor();
			
			return monitors.stream()
					.filter(a -> a.isCurrent())
					.findFirst()
					.orElseGet(null);
	}
	
	/**
	 * capture(ArrayList<Monitor> monitors)
	 * 
	 * Combines the bounds of multiple monitors and returns the
	 * resulting image of both monitors' contents.
	 * 
	 * @param monitors An array containing the monitors to be captured
	 * @return A combined image of both monitors' contents
	 */
	public Image capture(ArrayList<Monitor> monitors) {
		
		Rectangle bounds = new Rectangle();
		monitors.stream().forEach(a -> Rectangle.union(bounds, a.getBounds(), bounds) );
		
		Image image = null;
		
		try {
			
			// Minimize window so it does not appear in the screenshot
			frame.setExtendedState(Frame.ICONIFIED);
			
			// Wait for window to minimize, then create image
			while (frame.getExtendedState() != Frame.ICONIFIED) { }
			image = new Image(new Robot().createScreenCapture(bounds));
			
		} catch (Exception ex) {
			ex.printStackTrace();			
		}
		
		// Un-minimize window
		frame.setExtendedState(Frame.NORMAL);
		
		return image;
	}
	
	public Image capture(Monitor monitor) {
		return capture(new ArrayList<Monitor>(Arrays.asList(monitor)));
	}
	
	public Image capture() {
		return capture(new ArrayList<Monitor>(Arrays.asList(getCurrentMonitor())));
	}

}