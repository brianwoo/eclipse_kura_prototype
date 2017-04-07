/**
 * 
 */
package com.obs.bwoo.kuramqtt.client.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bwoo
 *
 */
public class ServiceStopConfigs extends PubSubConfigs
{

	public ServiceStopConfigs(String accountId, String requestId, String requesterClientId, String targetClientId)
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
				requestId +
				"/DEPLOY-V2/REPLY/" + 
				requesterClientId);
		
		return channels;
	}

	@Override
	public String getPublishChannel()
	{
		return "$EDC/" + 
				accountId + "/" +
				targetClientId + "/" +
				"DEPLOY-V2/EXEC/stop/";
	}

	@Override
	public Map<String, Object> getPayload()
	{
		Map<String, Object> payloadMap = new HashMap<>();		
		return payloadMap;
	}
	
}