package com.elsealabs.xshot.math;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * PointRectangle.java
 *
 * An extension of the Java implementation of the Rectangle shape
 * to include functionality not present in its original form.
 *
 * @author Connor Elsea
 * @version 1.0
 */
public class PointRectangle extends Rectangle {
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

	public PointRectangle(int x, int y, int i, int j)
	{
		super(x, y, i, j);
	}

	public PointRectangle(Point pnew, Point pbef)
	{
		//rectFromPoint(pbef, pnew);
		// TODO: How to do constructor like this
	}

	public static PointRectangle rectFromPoint(Point pnew, Point pbef)
	{
		// TODO: Refact PointRectangle to make more beautiful

		/* Lower Right Quadrant */
		if (pnew.x >= pbef.x && pnew.y >= pbef.y)
		{
			PointRectangle rec = new PointRectangle(pbef.x, pbef.y, pnew.x - pbef.x, pnew.y - pbef.y);

			rec.generateCornerPoints(pbef, pnew);
			rec.generateMidPoints();

			return rec;
		}

		/* Upper Right Quadrant */
		else if (pnew.x >= pbef.x && pnew.y <= pbef.y)
		{
			PointRectangle rec = new PointRectangle(pbef.x, pnew.y, pnew.x - pbef.x, pbef.y - pnew.y);

			rec.generateCornerPoints(pbef, pnew);
			rec.generateMidPoints();

			return rec;
		}

		/*  Upper Left Quadrant */
		else if (pnew.x <= pbef.x && pnew.y <= pbef.y)
		{
			PointRectangle rec = new PointRectangle(pnew.x, pnew.y, pbef.x - pnew.x, pbef.y - pnew.y);

			rec.generateCornerPoints(pbef, pnew);
			rec.generateMidPoints();

			return rec;
		}

		/* Lower Left Quadrant */
		else if (pnew.x <= pbef.x && pnew.y >= pbef.y)
		{
			PointRectangle rec = new PointRectangle(pnew.x, pbef.y, pbef.x - pnew.x, pnew.y - pbef.y);

			rec.generateCornerPoints(pbef, pnew);
			rec.generateMidPoints();

			return rec;
		}
		else
		{
			System.err.printf("\n\nPBEF:(%d, %d) PNEW:(%d, %d)\n\n", pbef.x, pbef.y, pnew.x, pnew.x);
			return null;
		}
	}

	public void generateMidPoints()
	{
		MiddleRight = CornerUpperRight.midPoint(CornerLowerRight);
		MiddleLeft  = CornerUpperLeft.midPoint(CornerLowerLeft);
		MiddleUp    = CornerUpperLeft.midPoint(CornerUpperRight);
		MiddleDown  = CornerLowerLeft.midPoint(CornerLowerRight);
	}

	public void generateCornerPoints(Point pbef, Point pnew)
	{
		CornerUpperLeft  = new XPoint(pbef.x, pbef.y);
		CornerUpperRight = new XPoint(pnew.x, pbef.y);
		CornerLowerLeft  = new XPoint(pbef.x, pnew.y);
		CornerLowerRight = new XPoint(pnew.x, pnew.y);
	}

	public ArrayList<XPoint> getPointsAsArray()
	{
		if (pointArray == null)
		{
			pointArray = new ArrayList<XPoint>(Arrays.asList(
				CornerUpperRight, CornerUpperLeft,
				CornerLowerRight, CornerLowerLeft,
				MiddleRight, MiddleLeft,
				MiddleUp, MiddleDown
			));
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

	public XPoint getMiddleRight() {
		return MiddleRight;
	}

	public XPoint getMiddleLeft() {
		return MiddleLeft;
	}

	public XPoint getMiddleUp() {
		return MiddleUp;
	}

	public XPoint getMiddleDown() {
		return MiddleDown;
	}

}