/**
 * 
 */
package com.obs.bwoo.kuramqtt.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bwoo
 *
 */
public class CommandResult
{
	private boolean statusCode = false;
	private String statusMsg = "";
	
	private String body;
	

	public boolean getStatusCode()
	{
		return statusCode;
	}


	public void setStatusCode(boolean statusCode)
	{
		this.statusCode = statusCode;
	}


	public String getStatusMsg()
	{
		return statusMsg;
	}


	public void setStatusMsg(String statusMsg)
	{
		this.statusMsg = statusMsg;
	}


	public String getBody()
	{
		return body;
	}


	public void setBody(String body)
	{
		this.body = body;
	}
	
	
	
	
}
