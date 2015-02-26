package com.elsealabs.xshot;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public class XRectangle extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private XPoint CornerUpperLeft;
	private XPoint CornerUpperRight;
	private XPoint CornerLowerLeft;
	private XPoint CornerLowerRight;
	
	private XPoint MiddleRight;
	private XPoint MiddleLeft;
	private XPoint MiddleUp;
	private XPoint MiddleDown;
	
	private ArrayList<XPoint> pointArray;
	
	public XRectangle(int x, int y, int i, int j) {
		super(x, y, i, j);
	}

	public static XRectangle rectFromPoint(Point pnew, Point pbef)
	{
		if (pnew.x > pbef.x && pnew.y > pbef.y)
		{
			XRectangle rec = new XRectangle(pbef.x, pbef.y, pnew.x - pbef.x, pnew.y - pbef.y);
			
			rec.CornerUpperLeft  = new XPoint(pbef.x, pbef.y);
			rec.CornerUpperRight = new XPoint(pnew.x, pbef.y);
			rec.CornerLowerLeft  = new XPoint(pbef.x, pnew.y);
			rec.CornerLowerRight = new XPoint(pnew.x, pnew.y);
			
			rec.MiddleRight = rec.CornerUpperRight.midPoint(rec.CornerLowerRight);
			rec.MiddleLeft  = rec.CornerUpperLeft.midPoint(rec.CornerLowerLeft);
			rec.MiddleUp    = rec.CornerUpperLeft.midPoint(rec.CornerUpperRight);
			rec.MiddleDown  = rec.CornerLowerLeft.midPoint(rec.CornerLowerRight);
			
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
	
	public ArrayList<XPoint> getPointsAsArray()
	{
		if (pointArray == null)
		{
			pointArray = new ArrayList<XPoint>(
				Arrays.asList(
					CornerUpperRight, CornerUpperLeft,
					CornerLowerRight, CornerLowerLeft,
					MiddleRight, MiddleLeft,
					MiddleUp, MiddleDown
				)
			);
		}
		return pointArray;
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