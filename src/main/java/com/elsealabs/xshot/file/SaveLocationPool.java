package com.elsealabs.xshot.file;

import java.util.ArrayList;
import java.util.List;

import com.elsea.stone.property.Property;
import com.elsea.stone.property.PropertyElement;
import com.elsea.stone.property.PropertyGroup;
import com.elsea.stone.property.PropertyPool;
import com.elsealabs.xshot.program.Program;
import com.elsealabs.xshot.views.ViewFileLocation;

public class SaveLocationPool {
	
	private List<SaveLocation> saveLocations;
	private Program program;
	private PropertyPool pool;
	private boolean updated = true;
	
	public SaveLocationPool()
	{
		saveLocations = new ArrayList<>();
		program       = Program.getInstance();
		pool          = program.getPool();
	}
	
	public void init()
	{
		SaveLocation current = null;
		
		List<PropertyElement> prop = pool.search().getElementsInGroup("locations");
		
		pool.show();
		
		System.out.println("SIZE: " + prop.size());
		
		for (PropertyElement e : prop)
		{
			if (e instanceof PropertyGroup)
			{
				PropertyGroup g = (PropertyGroup) e;
				String name = ((Property) g.getChildAt(0)).getCurrentValue();
				String path = ((Property) g.getChildAt(1)).getCurrentValue();
				String dflt = ((Property) g.getChildAt(2)).getCurrentValue();
				
				System.out.println("NAME: " + name);
				
				current = new SaveLocation(name, path);
				if (dflt.equals("true")) current.setDefault(true);
			}
		}
		
		program.addSaveLocation(current);
	}
	
	public void addNewUI(SaveLocation save)
	{
		ViewFileLocation view = new ViewFileLocation(save);
		view.setPool(this);
		view.setVisible(true);
	}
	
	public void saveLocation(SaveLocation save)
	{
		if (!saveLocations.contains(save))
		{
			saveLocations.add(save);

			PropertyGroup group = new PropertyGroup();
			group.setName("location");
			
			Property propName   = new Property();
			propName.setName("name");
			propName.setCurrentValue(save.getName());
			propName.setDefaultValue(save.getName());
			
			Property propPath   = new Property();
			propPath.setName("path");
			propPath.setCurrentValue(save.getPath());
			propPath.setDefaultValue(save.getPath());
			
			Property propDefault = new Property();
			propDefault.setName("default");
			propDefault.setCurrentValue(save.isDefault() + "");
			propDefault.setDefaultValue(save.isDefault() + "");
			
			group.addChild(propName);
			group.addChild(propPath);
			group.addChild(propDefault);
			
			pool.search().getGroup("locations").addChild(group);
			pool.save();
			
			updated = true;
		}
	}
	
	public void removeLocation(SaveLocation save)
	{
		if (saveLocations.contains(save))
		{
			PropertyGroup g = pool.search().getGroup("locations");
			saveLocations.remove(save);
			
			int i = 0;
			
			for (PropertyElement e : g.getChildren())
			{
				i += 1;
				
				if (e.getName().equals(save.getName()))
				{
					break;
				}
			}
			
			g.removeChild(g.getChildAt(i));
			pool.save();
			
			updated = true;
		}
	}
	
	public List<SaveLocation> getLocations()
	{
		return saveLocations;
	}
	
	public boolean isUpdated()
	{
		return updated;
	}
	
	public void setUpdated(boolean value)
	{
		this.updated = updated;
	}
}
