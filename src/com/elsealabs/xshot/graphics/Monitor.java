package com.elsealabs.xshot.graphics;

import java.awt.GraphicsDevice;
import java.awt.Rectangle;

public class Monitor {
	
	private GraphicsDevice device;
	private boolean current;
	
	public Monitor(GraphicsDevice device) {
		this.device = device;
		current = false;
	}
	
	public Rectangle getBounds() {
		return device.getDefaultConfiguration().getBounds();
	}
	
	public GraphicsDevice getDevice() {
		return device;
	}
	
	public boolean isCurrent() {
		return current;
	}
	
	public void setCurrent(boolean current) {
		this.current = current;
	}

}