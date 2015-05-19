package com.elsealabs.xshot.capture;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

/**
 * Monitor.java
 * 
 * A representation of a singular computer monitor.  This  representation
 * holds information about the GraphicsDevice and its main configuration,
 * as well as information about its dimensions and default status.
 *
 */
public class Monitor
{
	
	private GraphicsConfiguration config;
	private GraphicsEnvironment   env;
	private GraphicsDevice        device;
	
	private Rectangle bounds;
	private boolean   isdefault;
	
	public Monitor(GraphicsDevice graphicsDevice)
	{
		env       = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device    = graphicsDevice;
		config    = device.getDefaultConfiguration();
		isdefault = env.getDefaultScreenDevice() == device;
		bounds    = config.getBounds();
		
		System.out.println("NMB: " + bounds);
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public int getWidth()
	{
		return bounds.width;
	}
	
	public int getHeight()
	{
		return bounds.height;
	}
	
	public boolean isDefaultMonitor()
	{
		return isdefault;
	}
	
	public GraphicsConfiguration getConfiguration()
	{
		return config;
	}
	
	public GraphicsDevice getDevice()
	{
		return device;
	}
	
	@Override
	public String toString()
	{
		return "Monitor\n"
				+ "\ttype = " + device.getType() + "\n"
				+ "\tid   = " + device.getIDstring() + "\n"
				+ "\tdefault = " + isdefault;
	}

}