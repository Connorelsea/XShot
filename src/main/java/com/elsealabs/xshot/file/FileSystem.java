package com.elsealabs.xshot.file;

import java.io.File;
import java.util.HashMap;

public class FileSystem {
	
	public static enum OS
	{
		WINDOWS, OSX, LINUX
	}
	
	private OS      osType;
	private String  osName;
	private String  userPath;
	
	public static enum PATH
	{
		PROGRAM_DIR,
		STONE_FILE,
		SAVE_DEFAULT_DIR
	}
	
	private HashMap<String, File> files;
	
	private File location_programDir;
	private File location_stoneFile;
	private File location_saveDefaultDir;
	
	/**
	 * Determine the operating system and generate the paths needed by this
	 * program, some of which differ between each operating system.
	 */
	public void init()
	{
		files = new HashMap<String, File>();
		
		osName   = System.getProperty("os.name").toLowerCase();
		userPath = System.getProperty("user.home");
		
		if (osName.contains("windows"))
		{
			osType = OS.WINDOWS;
			location_programDir = new File(System.getenv("APPDATA") + "\\Capshot");
		}
		else if (osName.contains("mac"))
		{
			osType = OS.OSX;
			location_programDir = new File(getUserPath() + "/Library/Application Support/Capshot");
		}
		else if (osName.contains("nix") || osName.contains("nux"))
		{
			osType = OS.LINUX;
			location_programDir = new File(getUserPath() + "/Capshot");
		}
		else
		{
			osType = OS.WINDOWS;
			System.err.println("Error: Could not determine OS from os.name = \"" + osName + "\"");
			System.err.println("Warning: Defaulting OS to WINDOWS.");
		}
		
		location_stoneFile  = new File(location_programDir.getAbsolutePath() + "\\settings.xml");
	}
	
	/**
	 * Return the File object for the specified pre-defined location
	 * 
	 * @param path The pre-defined location
	 * @return The file object for the specified pre-defined location
	 */
	public File getPath(PATH path)
	{
		switch (path)
		{
			case PROGRAM_DIR:
				return location_programDir;
				
			case STONE_FILE:
				return location_stoneFile;
			
			case SAVE_DEFAULT_DIR:
				return location_saveDefaultDir;
	
			default:
				throw new IllegalArgumentException();
		}
	}
	
	public OS getOsType() {
		return osType;
	}
	
	public String getOsName() {
		return osName;
	}
	
	public String getUserPath() {
		return userPath;
	}

}