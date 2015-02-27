package com.elsealabs.xshot;

import java.util.ArrayList;
import java.util.Arrays;

public class Error {
	
	private Exception exception;
	private String message;
	private String smartTrace;
	private boolean doPopup;
	
	public Error(Exception exception, String message, boolean doPopup)
	{
		this.exception = exception;
		this.message = message;
		this.doPopup = doPopup;
		generateSmartTrace();
	}
	
	private void generateSmartTrace()
	{
		ArrayList<StackTraceElement> se = new ArrayList<StackTraceElement>(Arrays.asList(exception.getStackTrace()));
		StringBuilder sb = new StringBuilder();
		
		sb.append("> [Error : SmartTrace] {\n");
		sb.append("     | Exception Class: " + exception.getClass() + "\n");
		sb.append("     | Exception Message: \"" + exception.getMessage() + "\"\n");
		
		se.stream()
		.filter(e -> e.getLineNumber() > 0)
		.forEach(e ->
		{
			sb.append("\t ->\n");
			sb.append("\t   | File: " + e.getFileName() + " \n");
			sb.append("\t   | AT: " + e.getClassName() + "." + e.getMethodName() + "\n");
			sb.append("\t   | Line: " + e.getLineNumber() + "\n");
		});
		
		sb.append("}\n");
		
		smartTrace = sb.toString();
	}
	
	public boolean doesPopup() {
		return doPopup;
	}
	
	public Exception getException() {
		return exception;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getSmartTrace() {
		return smartTrace;
	}

}