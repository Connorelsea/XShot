package com.elsealabs.xshot.capture;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * CaptureDevice.java
 * 
 * Contains all of the information about the local graphical
 * environment and holds instances of the  user's  monitors.
 * Screen captures can be taken using Capture Device.
 *
 */
public class CaptureDevice
{
	private GraphicsEnvironment environment;
	private GraphicsDevice[]    screenDevices;
	private List<Monitor>       monitors;
	
	private Robot robot;

	/**
	 * Fires various setup methods to find environment information
	 * and create needed variables.
	 */
	public CaptureDevice()
	{
		setupEnvironment();
		setupMonitors();
		setupRobot();
	}
	
	/**
	 * This method merges the bounds of  two  or  more  monitors together,
	 * taking into account the offset that may be present in the operating
	 * system.
	 * 
	 * @param monitors The monitors whose bounds will be combined
	 * @return Bounds readjusted for the offset of monitors
	 */
	public Rectangle mergeBounds(List<Monitor> monitors)
	{	
		if (monitors.size() > 1)
		{
			Rectangle bounds = new Rectangle();
			
			monitors.stream().forEach(a -> {
				Rectangle.union(bounds, a.getBounds(), bounds);
			});
			
			return bounds;
		}
		else
		{
			return monitors.get(0).getBounds();
		}
	}
	
	/**
	 * Creates a capture of the monitor argument's dimensions.
	 * 
	 * @param monitor A monitor to be captured
	 * @return A capture of the monitor
	 */
	public BufferedImage captureMonitor(Monitor monitor)
	{
		return captureDimension(monitor.getBounds());
	}
	
	/**
	 *  Merges the dimensions of multiple monitors allowing
	 *  pictures that span multiple monitors.
	 *  
	 * @param monitors The list of monitors to be included
	 * @return One capture containing all specified monitors.
	 */
	public BufferedImage captureMonitors(List<Monitor> monitors)
	{
		return captureDimension(mergeBounds(monitors));
	}
	
	/**
	 * Shortcut for capturing all of the available monitors.
	 * 
	 * @return One capture containing all monitors.
	 */
	public BufferedImage captureAll()
	{
		return captureMonitors(getMonitors());
	}
	
	/**
	 * Takes a picture of the portion of  the  graphical  environment
	 * as defined by the argument-passed dimension. Returns a Capture
	 * object with this portion as its base image.
	 * 
	 * @param dimension The dimension to capture of the screen
	 * @return A capture with original bounds of said dimension
	 */
	public BufferedImage captureDimension(Rectangle dimension)
	{
		return robot.createScreenCapture(dimension);
	}
	
	/**
	 * This method loads the robot and handles any robot-related
	 * failures.
	 */
	public void setupRobot()
	{
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method loads the local graphical environment.
	 */
	public void setupEnvironment()
	{
		environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	}
	
	/**
	 * This method finds all the monitors that are involved with the
	 * local graphical environment.
	 */
	public void setupMonitors()
	{
		monitors      = new ArrayList<Monitor>();
		screenDevices = environment.getScreenDevices();
		
		for (GraphicsDevice device : screenDevices)
		{
			for (GraphicsConfiguration cfg : device.getConfigurations())
			{
				System.out.println(device.getIDstring() + " " + cfg.getBounds());
			}
			
			monitors.add(new Monitor(device));
		}
	}
	
	/**
	 * Returns a monitor at i position in the array of all
	 * available monitors.
	 * 
	 * @param i Position in the monitor array
	 * @return The monitor at position i in the monitor array.
	 */
	public Monitor getMonitor(int i)
	{
		return monitors.get(i);
	}
	
	/**
	 * Returns the default monitor as specified by the Graphics
	 * Environment.
	 * 
	 * @return The default monitor
	 */
	public Monitor getDefaultMonitor()
	{
		return monitors.stream()
				.filter(m -> m.isDefaultMonitor())
				.findFirst()
				.get();
	}
	
	/**
	 * Returns all of the available monitors in the current
	 * GraphicsEnvironment. Java does  not  allow  hardware
	 * changes to be updated when the JVM is on. Changes in
	 * the graphical environment will be reflected when the
	 * JVM is restarted.
	 * 
	 * @return All available monitors at the time of running
	 *         the JVM.
	 */
	public List<Monitor> getMonitors()
	{
		return monitors;
	}
	
}