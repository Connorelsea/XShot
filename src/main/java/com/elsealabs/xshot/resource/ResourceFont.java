package com.elsealabs.xshot.resource;

import java.awt.Font;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

public class ResourceFont extends Resource<Font>
{

	public ResourceFont(String name, Path path)
	{
		super(name, path);
	}

	@Override
	public boolean retrieveJAR()
	{
		try (
			InputStream stream = getClass().getResourceAsStream("/resources/font.ttf");
		)
		{
			setItem(Font.createFont(Font.TRUETYPE_FONT, stream));
			setBroken(false);
		}
		catch (Exception e)
		{
			setBroken(true);
			e.printStackTrace();
		}


		return false;
	}

	@Override
	public boolean retrieveIDE()
	{
		try
		{
			File file = new File("resources/font.ttf");
			setItem(Font.createFont(Font.TRUETYPE_FONT, file));
			setBroken(false);
		}
		catch (Exception e)
		{
			setBroken(true);
			e.printStackTrace();
		}

		return false;
	}

}