/**
 * 
 */
package com.obs.bwoo.kuramqtt.server.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.obs.bwoo.kuramqtt.common.PubSubConfig;

/**
 * @author bwoo
 *
 */
public class ServiceListingConfig extends PubSubConfig
{	
	public ServiceListingConfig(
			String accountId, 
			String requestId, 
			String requesterClientId, 
			String targetClientId)
	{
		super(accountId, requestId, requesterClientId, targetClientId);
	}
	
	
	@Override
	public List<String> getSubscribeChannels()
	{
		List<String> channels = new ArrayList<>();
		
		channels.add(
				"$EDC/" + 
				accountId + "/" + 
				requesterClientId +
				"/DEPLOY-V2/REPLY/" + 
				requestId);		
		
		return channels;
	}

	@Override
	public String getPublishChannel()
	{
		return "$EDC/" + 
				accountId + "/" +
				targetClientId + "/" +
				"DEPLOY-V2/GET/bundles";
	}

	@Override
	public Map<String, Object> getPayload()
	{
		Map<String, Object> payloadMap = new HashMap<>();
		return payloadMap;
	}
	
}
