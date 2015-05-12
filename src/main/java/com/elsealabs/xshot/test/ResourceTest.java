package com.elsealabs.xshot.test;

import com.elsealabs.xshot.resource.ResourceFont;

import java.nio.file.Paths;

public class ResourceTest
{
	public static void main(String[] args)
	{
		ResourceFont resFont = new ResourceFont("font", Paths.get("resources/font.ttf"));
		resFont.retrieve();

		System.out.println(resFont);
	}
}