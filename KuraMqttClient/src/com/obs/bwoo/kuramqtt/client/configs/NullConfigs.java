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
public class NullConfigs extends PubSubConfigs
{

	public NullConfigs(String accountId, String requestId, String requesterClientId, String targetClientId)
	{
		super(accountId, requestId, requesterClientId, targetClientId);
	}

	/* (non-Javadoc)
	 * @see com.obs.bwoo.kuramqtt.client.configs.PubSubConfigs#getSubscribeChannels()
	 */
	@Override
	public List<String> getSubscribeChannels()
	{
		return new ArrayList<String>();
	}

	/* (non-Javadoc)
	 * @see com.obs.bwoo.kuramqtt.client.configs.PubSubConfigs#getPublishChannel()
	 */
	@Override
	public String getPublishChannel()
	{
		return "";
	}

	/* (non-Javadoc)
	 * @see com.obs.bwoo.kuramqtt.client.configs.PubSubConfigs#getPayload()
	 */
	@Override
	public Map<String, Object> getPayload()
	{
		return new HashMap<>();
	}

}
