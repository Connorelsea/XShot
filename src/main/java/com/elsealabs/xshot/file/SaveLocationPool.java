package com.elsealabs.xshot.file;

import java.util.ArrayList;
import java.util.List;

import com.elsea.stone.property.Property;
import com.elsea.stone.property.PropertyElement;
import com.elsea.stone.property.PropertyGroup;
import com.elsea.stone.property.PropertyPool;
import com.elsealabs.xshot.program.Program;

public class SaveLocationPool {
	
	private List<SaveLocation> saveLocations;
	private Program program;
	private PropertyPool pool;
	
	public SaveLocationPool()
	{
		saveLocations = new ArrayList<>();
		program       = Program.getInstance();
		pool          = program.getPool();
	}
	
	public void init()
	{
		List<PropertyElement> prop = pool.search().getElementsInGroup("locations");
		
		for (PropertyElement e : prop)
		{
			if (e instanceof PropertyGroup)
			{
				PropertyGroup g = (PropertyGroup) e;
				String name = ((Property) g.getChildAt(0)).getCurrentValue();
				String path = ((Property) g.getChildAt(1)).getCurrentValue();
				String dflt = ((Property) g.getChildAt(2)).getCurrentValue();
				
				SaveLocation current = new SaveLocation(name, path);
				if (dflt.equals("true")) current.setDefault(true);
				program.addSaveLocation(current);
			}
		}
	}
	
	public void addNewUI()
	{
		
	}
	
	public List<SaveLocation> getLocations()
	{
		return saveLocations;
	}
	
	public void addLocation(SaveLocation saveLocation)
	{
		saveLocations.add(saveLocation);
	}

}
