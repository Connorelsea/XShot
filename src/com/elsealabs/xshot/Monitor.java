package com.elsealabs.xshot;

import java.awt.GraphicsDevice;

public class Monitor {
	
	private GraphicsDevice device;
	private boolean current;
	
	public Monitor(GraphicsDevice device) {
		this.device = device;
		current = false;
	}
	
	public GraphicsDevice getDevice() {
		return device;
	}
	
	public void setDevice(GraphicsDevice device) {
		this.device = device;
	}
	
	public boolean isCurrent() {
		return current;
	}
	
	public void setCurrent(boolean current) {
		this.current = current;
	}

}