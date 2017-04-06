/**
 * 
 */
package com.obs.bwoo.kuramqtt.start;

import com.amitinside.mqtt.client.kura.message.KuraPayload;

/**
 * @author bwoo
 *
 */
public class KuraCommand {

	public enum Type {
		
		DOWNLOAD_1,
		DOWNLOAD_2
	}
	
	private Type commandType;
	
	/**
	 * 
	 */
	public KuraCommand(String command) 
	{
		if (command.equals("download1"))
			commandType = Type.DOWNLOAD_1;
		
		else if (command.equals("download2"))
			commandType = Type.DOWNLOAD_2;
	}
	
	
	public String getChannel(String accountId, String targetClientId)
	{
		KuraChannelFactory factory = new KuraChannelFactory(accountId, targetClientId);
		
		String channel = factory.getChannel(commandType);
		return channel;
	}
	
	
	public KuraPayload getPayload(String requestId, String requesterClientId)
	{
		KuraPayloadFactory factory = new KuraPayloadFactory(requestId, requesterClientId);
		
		KuraPayload payload = factory.getPayload(commandType);
		return payload;
	}

}
