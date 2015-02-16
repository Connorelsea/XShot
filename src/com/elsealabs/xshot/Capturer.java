package com.elsealabs.xshot;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
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
	
	public Capturer(JFrame attachedFrame) {
		this.frame = attachedFrame;
		_initDevices();
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
	 * capture(ArrayList<Monitor> monitors)
	 * 
	 * Combines the bounds of multiple monitors and returns the
	 * resulting image of both monitors' contents.
	 * 
	 * @param monitors An array containing the monitors to be captured
	 * @return A combined image of both monitors' contents
	 */
	public BufferedImage capture(ArrayList<Monitor> monitors) {
		
		Rectangle bounds = new Rectangle();
		monitors.stream().forEach(a -> a.getBounds().union(bounds));
		
		try {
			return new Robot().createScreenCapture(bounds);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}

}