/**
 * 
 */
package com.obs.bwoo.kuramqtt.server.utils;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

/**
 * @author bwoo
 *
 */
public class JsonUtils
{

	public static JSON fromXml(String xmlString)
	{
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(xmlString);
		
		//System.out.println("JSON: " + json);
		
		return json;
	}
	
}
