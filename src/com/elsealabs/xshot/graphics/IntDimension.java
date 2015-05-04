package com.elsealabs.xshot.graphics;

public class IntDimension
{
	private IntPoint northWest;
	private IntPoint northEast;
	private IntPoint southEast;
	private IntPoint southWest;

	private int width;
	private int height;
	
	public static enum DIRECTION
	{
		NORTH, SOUTH, EAST, WEST,
		NORTHWEST, NORTHEAST, SOUTHEAST, SOUTHWEST
	}
	
	public IntDimension(IntPoint northWest, int width, int height)
	{
		this.width  = width;
		this.height = height;
		
		setPosition(northWest);
	}
	
	@Override
	public String toString() {
		return "IntDimension: \n\tLenghts:"
				+ "\n\t\twidth="  + width
				+ "\n\t\theight=" + height
				+"\n\tPoints"
				+ "\n\t\t" + northWest + "-" + northEast
				+ "\n\t\t" + southWest + "-" + southEast;
	};
	
	public void setPosition(IntPoint northWest)
	{
		this.northWest = northWest;
		this.northEast = new IntPoint(northWest.getX() + width, northWest.getY());
		this.southEast = new IntPoint(northWest.getX() + width, northWest.getY() + height);
		this.southWest = new IntPoint(northWest.getX(), northWest.getY() + height);
	}
	
	public void translate(DIRECTION direction, int amount)
	{
		// TODO: IntDimension.translate(...)
	}
	
	public void contains(IntPoint point)
	{
		// TODO: IntDimension.contains(...)
	}
	
	public void expand(DIRECTION direction, int amount)
	{
		if (direction == DIRECTION.NORTH)
		{
			height = height + amount;
			int expandY = northWest.getY() - amount;
			
			if (expandY > 0)
			{
				northWest = new IntPoint(northWest.getX(), expandY);
				northEast = new IntPoint(northWest.getX(), expandY);
			}
		}
		
		else if (direction == DIRECTION.SOUTH)
		{
			height = height + amount;
			int expandY = southWest.getY() + amount;
			
			southWest = new IntPoint(southWest.getX(), expandY);
			southEast = new IntPoint(southEast.getX(), expandY);
		}
		
		else if (direction == DIRECTION.WEST)
		{
			width = width + amount;
			int expandX = northWest.getX() - amount;
			
			if (expandX > 0)
			{
				northWest = new IntPoint(expandX, northWest.getY());
				southWest = new IntPoint(expandX, southWest.getY());
			}
		}
		
		else if (direction == DIRECTION.EAST)
		{
			width = width + amount;
			int expandX = northEast.getX() + amount;
			
			northEast = new IntPoint(expandX, northEast.getY());
			southEast = new IntPoint(expandX, southEast.getY());
		}
		
		else if (direction == DIRECTION.NORTHWEST)
		{
			expand(DIRECTION.NORTH, amount);
			expand(DIRECTION.WEST,  amount);
		}
		
		else if (direction == DIRECTION.SOUTHWEST)
		{
			expand(DIRECTION.SOUTH, amount);
			expand(DIRECTION.WEST,  amount);
		}
		
		else if (direction == DIRECTION.SOUTHEAST)
		{
			expand(DIRECTION.SOUTH, amount);
			expand(DIRECTION.EAST,  amount);
		}
		
		else if (direction == DIRECTION.NORTHWEST)
		{
			expand(DIRECTION.NORTH, amount);
			expand(DIRECTION.WEST,  amount);
		}
	}
}
