package com.elsealabs.xshot.program;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.List;

import com.elsea.stone.property.PropertyPool;
import com.elsea.stone.property.PropertyPoolReader;
import com.elsealabs.xshot.file.FileSystem;
import com.elsealabs.xshot.file.SaveLocation;
import com.elsealabs.xshot.file.SaveLocationPool;
import com.elsealabs.xshot.graphics.ColorContainer;
import com.elsealabs.xshot.graphics.ColorGlobalSet;
import com.elsealabs.xshot.views.ViewMainModern;

public class Program
{

	private static Program program;
	private FileSystem fileSystem;
	private PropertyPool pool;
	private ColorGlobalSet colors;
	private SaveLocationPool saveLocations;

	private BUILD_TYPE buildType = BUILD_TYPE.JAR;

	public enum BUILD_TYPE
	{
		IDE, JAR
	}

	public static Program getInstance()
	{
		if (program == null) {
			program = new Program();
		}
		return program;
	}
	
	private Program() {
		System.out.println("Creating new program object.");
	}

	/**
	 * Runs the methods needed to start the program.
	 */
	public void run()
	{
		fileSystem = new FileSystem();
		fileSystem.run();
		
		initPool();
		
		saveLocations = new SaveLocationPool();
		saveLocations.init();
		
		defineColors();
		EventQueue.invokeLater(ViewMainModern::new);
	}
	
	public void initPool()
	{
		System.out.println("Program: Attempting to read in Property Pool");
		
		PropertyPoolReader reader = new PropertyPoolReader();
		
		if (reader.read(fileSystem.getPath(FileSystem.PATH.STONE_FILE)))
		{
			pool = reader.getPropertyPool();
			
			System.out.println("Pool is originally not null...");
			System.out.println("Calling from object " + this);
			System.out.println(pool);
		}
		else
		{
			System.err.println("Fatal Error: Unable to read Property Pool");
		}
	}
	  
	private void defineColors()
	{
		colors = ColorGlobalSet.getInstance();

		HashMap<String, Color> main_red_colors = new HashMap<>();
		main_red_colors.put("dark", new Color(43, 6, 6));
		main_red_colors.put("med_dark", new Color(130, 24, 24));
		main_red_colors.put("med_light", new Color(178, 52, 52));
		main_red_colors.put("light", new Color(226, 86, 86));

		ColorContainer main_red = new ColorContainer("main", "red", main_red_colors);
		main_red.setDefault(true);
		colors.addContainer(main_red);

		HashMap<String, Color> main_gray_colors = new HashMap<>();
		main_gray_colors.put("dark", new Color(40, 40, 40));
		main_gray_colors.put("med_dark", new Color(73, 73, 73));
		main_gray_colors.put("med_light", new Color(126, 126, 126));
		main_gray_colors.put("light", new Color(182, 182, 182));

		ColorContainer main_gray = new ColorContainer("main", "red", main_gray_colors);
		main_gray.setDefault(false);
		colors.addContainer(main_gray);

		HashMap<String, Color> main_green_colors = new HashMap<>();
		main_green_colors.put("dark", new Color(15, 39, 12));
		main_green_colors.put("med_dark", new Color(43, 83, 37));
		main_green_colors.put("med_light", new Color(76, 130, 68));
		main_green_colors.put("light", new Color(123, 192, 121));

		ColorContainer main_green = new ColorContainer("main", "red", main_green_colors);
		main_green.setDefault(false);
		colors.addContainer(main_green);

		HashMap<String, Color> main_blue_colors = new HashMap<>();
		main_blue_colors.put("dark", new Color(4, 19, 40));
		main_blue_colors.put("med_dark", new Color(8, 38, 79));
		main_blue_colors.put("med_light", new Color(12, 54, 112));
		main_blue_colors.put("light", new Color(18, 75, 153));

		ColorContainer main_blue = new ColorContainer("main", "red", main_blue_colors);
		main_blue.setDefault(false);
		colors.addContainer(main_blue);
	}

	public BUILD_TYPE getBuildType()
	{
		return buildType;
	}
	
	public PropertyPool getPool()
	{
		System.out.println("Attempting to get pool later...");
		System.out.println("Calling from object " + this);
		System.out.println(pool);
		return pool;
	}

	public List<SaveLocation> getSaveLocations() {
		return saveLocations.getLocations();
	}
	
	public void addSaveLocation(SaveLocation saveLocation)
	{
		saveLocations.saveLocation(saveLocation);
	}
	
	public SaveLocationPool getSaveLocationPool()
	{
		return saveLocations;
	}
	
	public FileSystem getFileSystem()
	{
		return fileSystem;
	}

}