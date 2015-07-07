package com.elsea.stone.property;

import java.io.File;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * PropertyPoolReader
 * 
 * Reads in Stone-formatted XML data and returns it  to its original
 * Property Pool state. The resulting Property Pool is  stored  as a
 * member of the Property Pool Reader object and can be accessed via
 * the accessor method getPropertyPool.
 * 
 * @author Connor M. Elsea
 */
public class PropertyPoolReader {
	
	private PropertyPool result;

	/**
	 * Reads in Stone-formatted XML data and returns it  to its original
	 * Property Pool state. The resulting Property Pool is  stored  as a
	 * member of the Property Pool Reader object and can be accessed via
	 * the accessor method getPropertyPool.
	 * 
	 * @param file The file to read in from. Should be an XML file.
	 * @return Whether or not the operating was successful. Minor, non-file, errors
	 *         still will return true. Look at console warnings for  more  detailed
	 *         failure alerts for minor issues such as formatting or altered  data.
	 */
	public boolean read(File file)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList list = doc.getChildNodes();
			result 		  = new PropertyPool();
			
			// Parse, setting parent as the first node
			parse(result, list.item(0));
			
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * A recursive method that loops through the Document's nodes and builds
	 * a Property Pool from them.
	 * 
	 * @param pool The resulting pool
	 * @param parent The parent object to pass down the recursive calls
	 */
	private void parse(PropertyPool pool, Node parent)
	{
		if (parent.hasChildNodes())
		{
			NodeList list = parent.getChildNodes();
			
			for (int i = 0; i < list.getLength(); i++)
			{
				Node current = list.item(i);
				
				// Some elements may be random text nodes, so we must only use the nodes
				// that are important to our data.
				
				if (current.getNodeType() == Node.ELEMENT_NODE)
				{
					// Check group attribute, determine if node is a group
					Node attr = current.getAttributes().item(0);
					boolean group = attr.getNodeName().equals("group") && attr.getNodeValue().equals("true");
					
					// Check type attribute, determine type of node
					Node typeAttr = current.getAttributes().item(1);
					String type = typeAttr.getNodeValue();
					
					// If the current node should be a PropertyGroup
					if (group)
					{
						pool.group(current.getNodeName());
						if (type != null) pool.type(type);
						else pool.type("");
						parse(pool, current);
						pool.end();
					}
					// If the current node should be a PropertyElement
					else
					{
						NodeList propertyChildren = current.getChildNodes();
						
						Node defaultNode    = null;
						Node currentNode    = null;
						
						String nodeName     = current.getNodeName();
						String defaultValue = null;
						String currentValue = null;
						
						// Some children will be text nodes, etc  So we loop through all
						// of them and find the ones that are not, which, in a  properly
						// formatted document, will mean that they  are  the  nodes  for 
						// default and current.
						
						for (int c = 0; c < propertyChildren.getLength(); c++)
						{
							Node child = propertyChildren.item(c);
							
							if (child.getNodeType() == Node.ELEMENT_NODE)
							{
								if (child.getNodeName().equals("default"))      defaultNode = child;
								else if (child.getNodeName().equals("current")) currentNode = child;
							}
						}
						
						if (defaultNode != null) defaultValue = defaultNode.getChildNodes().item(0).getNodeValue();
						if (currentNode != null) currentValue = currentNode.getChildNodes().item(0).getNodeValue();
						
						pool.property(nodeName, defaultValue, currentValue);
						if (type != null) pool.type(type);
					}
					
				}
				
			}
		}
		
	}
	
	public PropertyPool getPropertyPool() {
		return result;
	}
	
}