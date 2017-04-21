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
public class ServiceStartConfig extends PubSubConfig
{
	public static final String BUNDLE_ID = "bundleId";
	
	private String bundleId;

	public ServiceStartConfig(
			String accountId, 
			String requestId, 
			String requesterClientId, 
			String targetClientId, 
			Map<String, Object> extraConfig)
	{
		super(accountId, requestId, requesterClientId, targetClientId);
		
		this.bundleId = (String) extraConfig.get(BUNDLE_ID);
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
				"DEPLOY-V2/EXEC/start/" + bundleId;
	}

	@Override
	public Map<String, Object> getPayload()
	{
		Map<String, Object> payloadMap = new HashMap<>();
		return payloadMap;
	}
	
}
