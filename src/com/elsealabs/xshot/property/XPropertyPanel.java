package com.elsealabs.xshot.property;

import javax.swing.JPanel;

public abstract class XPropertyPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private XProperty property;
	
	public XPropertyPanel(XProperty property)
	{
		this.property = property;
	}
	
	/**
	 * Upon instantiation, the programmer will define the
	 * Swing components inside of this panel  using  this
	 * method and the anonymous inner class syntax.
	 */
	public abstract void define();
	
	public XProperty getProperty()
	{
		return property;
	}
}