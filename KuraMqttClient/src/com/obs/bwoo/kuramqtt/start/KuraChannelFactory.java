/**
 * 
 */
package com.obs.bwoo.kuramqtt.start;

import com.obs.bwoo.kuramqtt.start.KuraCommand.Type;

/**
 * @author bwoo
 *
 */
public class KuraChannelFactory {

	private String clientId;
	private String accountId;
	
	/**
	 * Ctor
	 */
	public KuraChannelFactory(String accountId, String targetClientId) {
		
		this.clientId = targetClientId;
		this.accountId = accountId;
	}
	
	
	public String getChannel(Type type)
	{
		String channel = "";
		
		if (Type.DOWNLOAD_1 == type)
		{
			channel = "$EDC/" + 
					  accountId + "/" +
					  clientId + "/" +
					  "DEPLOY-V2/EXEC/download";
		}
		else if (Type.DOWNLOAD_2 == type)
		{
			channel = "$EDC/" + 
					  accountId + "/" +
					  clientId + "/" +
					  "DEPLOY-V2/EXEC/download";
		}		
		
		return channel;
	}

}
