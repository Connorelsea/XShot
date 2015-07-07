package com.elsea.stone.property;

import java.io.File;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * Writes the a Property Pool, formatted as XML, to the file system.
 * 
 * @author Connor M. Elsea
 *
 */
public class PropertyPoolWriter {

	public boolean write(Document doc, File to)
	{
		try
		{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(to);
			transformer.transform(source, result);
			
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean write(PropertyPool pool, File to)
	{
		return write(pool.toXMLDocument(), to);
	}
	
}