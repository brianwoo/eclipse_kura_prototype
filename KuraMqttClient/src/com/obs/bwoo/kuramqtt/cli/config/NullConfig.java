/**
 * 
 */
package com.obs.bwoo.kuramqtt.cli.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.obs.bwoo.kuramqtt.common.PubSubConfig;

/**
 * @author bwoo
 *
 */
public class NullConfig extends PubSubConfig
{

	public NullConfig(String accountId, String requestId, String requesterClientId, String targetClientId)
	{
		super(accountId, requestId, requesterClientId, targetClientId);
	}

	/* (non-Javadoc)
	 * @see com.obs.bwoo.kuramqtt.cli.config.PubSubConfig#getSubscribeChannels()
	 */
	@Override
	public List<String> getSubscribeChannels()
	{
		return new ArrayList<String>();
	}

	/* (non-Javadoc)
	 * @see com.obs.bwoo.kuramqtt.cli.config.PubSubConfig#getPublishChannel()
	 */
	@Override
	public String getPublishChannel()
	{
		return "";
	}

	/* (non-Javadoc)
	 * @see com.obs.bwoo.kuramqtt.cli.config.PubSubConfig#getPayload()
	 */
	@Override
	public Map<String, Object> getPayload()
	{
		return new HashMap<>();
	}

}
