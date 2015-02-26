package com.elsealabs.xshot;

import java.awt.Point;

public class XPoint extends Point {

	public XPoint(int x, int y) {
		super(x, y);
	}
	
	public XPoint midPoint(XPoint p)
	{
	     return new XPoint((int) (p.x + x) / 2, (int) (p.y + y) / 2);
	}
	
}