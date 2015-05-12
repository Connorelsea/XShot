package com.elsealabs.xshot.program;

import com.elsealabs.xshot.graphics.ColorContainer;
import com.elsealabs.xshot.graphics.ColorGlobalSet;
import com.elsealabs.xshot.views.ViewMainModern;

import java.awt.*;
import java.util.HashMap;

public class Program
{

	private static Program program;
	private ColorGlobalSet colors;

	private BUILD_TYPE buildType = BUILD_TYPE.JAR;

	public enum BUILD_TYPE
	{
		IDE, JAR
	}

	public static Program getInstance()
	{
		if (program == null)
		{
			program = new Program();
		}
		return program;
	}

	public void run()
	{
		init();
		EventQueue.invokeLater(ViewMainModern::new);
	}

	private void init()
	{
		defineColors();
	}

	private void defineColors()
	{
		colors = ColorGlobalSet.getInstance();

		HashMap<String, Color> main_red_colors = new HashMap<>();
		main_red_colors.put("dark", new Color(43, 6, 6));
		main_red_colors.put("med_dark", new Color(130, 24, 24));
		main_red_colors.put("med_light", new Color(178, 52, 52));
		main_red_colors.put("light", new Color(226, 86, 86));

		ColorContainer main_red = new ColorContainer("main", "red",
				main_red_colors);
		main_red.setDefault(true);
		colors.addContainer(main_red);

		HashMap<String, Color> main_gray_colors = new HashMap<>();
		main_gray_colors.put("dark", new Color(40, 40, 40));
		main_gray_colors.put("med_dark", new Color(73, 73, 73));
		main_gray_colors.put("med_light", new Color(126, 126, 126));
		main_gray_colors.put("light", new Color(182, 182, 182));

		ColorContainer main_gray = new ColorContainer("main", "red",
				main_gray_colors);
		main_gray.setDefault(false);
		colors.addContainer(main_gray);

		HashMap<String, Color> main_green_colors = new HashMap<>();
		main_green_colors.put("dark", new Color(15, 39, 12));
		main_green_colors.put("med_dark", new Color(43, 83, 37));
		main_green_colors.put("med_light", new Color(76, 130, 68));
		main_green_colors.put("light", new Color(123, 192, 121));

		ColorContainer main_green = new ColorContainer("main", "red",
				main_green_colors);
		main_green.setDefault(false);
		colors.addContainer(main_green);

		HashMap<String, Color> main_blue_colors = new HashMap<>();
		main_blue_colors.put("dark", new Color(4, 19, 40));
		main_blue_colors.put("med_dark", new Color(8, 38, 79));
		main_blue_colors.put("med_light", new Color(12, 54, 112));
		main_blue_colors.put("light", new Color(18, 75, 153));

		ColorContainer main_blue = new ColorContainer("main", "red",
				main_blue_colors);
		main_blue.setDefault(false);
		colors.addContainer(main_blue);
	}

	public BUILD_TYPE getBuildType()
	{
		return buildType;
	}

}