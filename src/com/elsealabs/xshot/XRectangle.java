package com.elsealabs.xshot;

import java.awt.Point;
import java.awt.Rectangle;

public class XRectangle extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private Point CornerUpperLeft;
	private Point CornerUpperRight;
	private Point CornerLowerLeft;
	private Point CornerLowerRight;
	
	public XRectangle(int x, int y, int i, int j) {
		super(x, y, i, j);
	}

	public static XRectangle rectFromPoint(Point pnew, Point pbef)
	{
		if (pnew.x > pbef.x && pnew.y > pbef.y)
		{
			XRectangle rec = new XRectangle(pbef.x, pbef.y, pnew.x - pbef.x, pnew.y - pbef.y);
			
			rec.CornerUpperLeft = new Point(pbef.x, pbef.y);
			
			return rec;
		}
		else if (pnew.x > pbef.x && pnew.y < pbef.y) {
			return new XRectangle(pbef.x, pnew.y, pnew.x - pbef.x, pbef.y - pnew.y);
		}
		else if (pnew.x < pbef.x && pnew.y < pbef.y) {
			return new XRectangle(pnew.x, pnew.y, pbef.x - pnew.x, pbef.y - pnew.y);
		}
		else if (pnew.x < pbef.x && pnew.y > pbef.y) {
			return new XRectangle(pnew.x, pbef.y, pbef.x - pnew.x, pnew.y - pbef.y);
		}
		return new XRectangle(1, 1, 1, 1);
	}

	public Point getCornerUpperLeft() {
		return CornerUpperLeft;
	}

	public Point getCornerUpperRight() {
		return CornerUpperRight;
	}

	public Point getCornerLowerLeft() {
		return CornerLowerLeft;
	}

	public Point getCornerLowerRight() {
		return CornerLowerRight;
	}
	
}