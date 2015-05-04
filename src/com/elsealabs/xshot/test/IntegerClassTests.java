package com.elsealabs.xshot.test;

import com.elsealabs.xshot.graphics.IntDimension;
import com.elsealabs.xshot.graphics.IntPoint;
import com.elsealabs.xshot.graphics.IntDimension.DIRECTION;

public class IntegerClassTests
{
	public static void main(String[] args)
	{
		testExpand();
	}
	
	public static void testExpand()
	{
		// Create initial object
		IntDimension i = new IntDimension(new IntPoint(0, 0), 10, 10);
		System.out.println(i);
		
		// Expand east
		i.expand(DIRECTION.EAST, 10);
		System.out.println(i);
		
		// Expand east negative
		i.expand(DIRECTION.EAST, -10);
		System.out.println(i);
		
		// Expand out of bounds
		i.expand(DIRECTION.NORTH, 5);
		System.out.println(i);
		
		// Expand south
		i.expand(DIRECTION.SOUTH, 5);
		System.out.println(i);
		
		// Expand to zero
		i.expand(DIRECTION.SOUTH, -15);
		System.out.println(i);
		
	}
}