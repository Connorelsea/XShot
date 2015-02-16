package com.elsealabs.xshot;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 */
import javax.swing.JFrame;

public class Capturer {
	
	private GraphicsEnvironment graphics;
	private ArrayList<Monitor> monitors;
	private JFrame frame;
	
	public Capturer() {
		_initDevices();
	}
	
	private void _initDevices() {
		
		graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
		monitors = new ArrayList<Monitor>();
		
		ArrayList<GraphicsDevice> devices = new ArrayList<GraphicsDevice>(
			Arrays.asList(graphics.getScreenDevices())
		);
		
		devices.stream()
			.forEach(a -> monitors.add(new Monitor(a)));
		
	}
	
	public void findCurrentMonitor() {
		
		GraphicsDevice current = frame.getGraphicsConfiguration().getDevice();
		monitors.stream().forEach(a -> a.setCurrent(a.getDevice() == current));
		
	}
	
	public void setAttachedFrame(JFrame frame) {
		
	}
	
	public Screenshot captureMonitor(int i) {
		
		try {
			
			GraphicsDevice gd = monitors.get(i - 1);
			Robot robot = new Robot(gd);
			Rectangle bounds = gd.getDefaultConfiguration().getBounds();
			
			return new Screenshot(robot.createScreenCapture(bounds));
			
		} catch (AWTException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}