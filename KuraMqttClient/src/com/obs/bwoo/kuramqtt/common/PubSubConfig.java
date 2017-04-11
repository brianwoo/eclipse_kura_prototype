/**
 * 
 */
package com.obs.bwoo.kuramqtt.common;

import java.util.List;
import java.util.Map;

/**
 * @author bwoo
 *
 */
public abstract class PubSubConfig
{
	protected String accountId;
	protected String requesterClientId;
	protected String requestId;
	protected String targetClientId;
	
	protected PubSubConfig(String accountId, String requestId, String requesterClientId, String targetClientId)
	{
		this.accountId = accountId;
		this.requestId = requestId;
		this.requesterClientId = requesterClientId;
		this.targetClientId = targetClientId;
	}
		
	public String getAccountId()
	{
		return accountId;
	}

	public String getRequesterClientId()
	{
		return requesterClientId;
	}

	public String getTargetClientId()
	{
		return targetClientId;
	}

	public String getRequestId()
	{
		return requestId;
	}

	public abstract List<String> getSubscribeChannels();
	
	public abstract String getPublishChannel();
	
	public abstract Map<String, Object> getPayload();
	
}
