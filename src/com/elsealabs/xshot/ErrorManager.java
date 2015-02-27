package com.elsealabs.xshot;

import java.util.ArrayList;

public class ErrorManager {
	
	private static ErrorManager manager;
	private ArrayList<Error> errors;
	private boolean showMessages;
	private boolean showSmartTraces;
	private boolean showFullTraces;
	private boolean showPopups;
	
	public static ErrorManager getInstance()
	{
		if (manager == null)
		{
			manager = new ErrorManager();
			manager.errors = new ArrayList<Error>();
			manager.showMessages = true;
			manager.showSmartTraces = true;
			manager.showPopups = true;
			manager.showFullTraces = false;
		}
		return manager;
	}
	
	public void newError(Error error)
	{
		if (showMessages)
		{
			System.err.printf("> [Error : Message] %s\n", error.getMessage());
		}
		if (showSmartTraces)
		{
			System.err.printf(error.getSmartTrace());
		}
		if (showPopups)
		{
			// TODO: Show popup if error has one
		}
		
		errors.add(error);
	}

}