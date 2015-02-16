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
	private ArrayList<GraphicsDevice> monitors;
	private JFrame frame;
	
	public Capturer() {
		_initDevices();
	}
	
	private void _initDevices() {
		
		graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
		monitors = new ArrayList<GraphicsDevice>(Arrays.asList(graphics.getScreenDevices()));
		
	}
	
	public void findCurrentMonitor() {
		frame.getGraphicsConfiguration().getDevice();
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