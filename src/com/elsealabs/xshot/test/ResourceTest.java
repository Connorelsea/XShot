package com.elsealabs.xshot.test;

import java.nio.file.Paths;

import com.elsealabs.xshot.resource.ResourceFont;

public class ResourceTest
{
	public static void main(String[] args)
	{
		ResourceFont resFont = new ResourceFont("font", Paths.get("res/font.ttf"));
		resFont.retrieve();
		
		System.out.println(resFont);
	}
}