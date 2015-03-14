package com.elsealabs.xshot.graphics;

import java.awt.Point;

public class XPoint extends Point {
	private static final long serialVersionUID = 1L;

	public XPoint(int x, int y) {
		super(x, y);
	}
	
	public XPoint(Point point) {
		super(point);
	}
	
	public XPoint midPoint(XPoint p)
	{
	     return new XPoint((int) (p.x + x) / 2, (int) (p.y + y) / 2);
	}
	
}