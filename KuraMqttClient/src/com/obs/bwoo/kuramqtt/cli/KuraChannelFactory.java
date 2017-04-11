/**
 * 
 */
package com.obs.bwoo.kuramqtt.cli;

import com.obs.bwoo.kuramqtt.common.KuraCommand;
import com.obs.bwoo.kuramqtt.common.KuraCommand.Type;

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
		else if (Type.SERVICE_START == type)
		{
			channel = "$EDC/" + 
					  accountId + "/" +
					  clientId + "/" +
					  "DEPLOY-V2/EXEC/start/124";
		}
		else if (Type.SERVICE_STOP == type)
		{
			channel = "$EDC/" + 
					  accountId + "/" +
					  clientId + "/" +
					  "DEPLOY-V2/EXEC/stop/124";
		}		
		
		return channel;
	}

}
