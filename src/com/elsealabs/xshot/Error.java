package com.elsealabs.xshot;

import java.util.ArrayList;
import java.util.Arrays;

public class Error {
	
	private Exception exception;
	private String message;
	private String smartTrace;
	
	public Error(Exception exception, String message)
	{
		this.exception = exception;
		this.message = message;
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