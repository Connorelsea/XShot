package com.elsealabs.xshot.graphics;

public class IntPoint
{	
	int x, y;
	
	public IntPoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
}