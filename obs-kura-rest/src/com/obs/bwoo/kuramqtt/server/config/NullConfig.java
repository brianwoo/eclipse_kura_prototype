/**
 * 
 */
package com.obs.bwoo.kuramqtt.server.config;

import java.util.List;
import java.util.Map;

import com.obs.bwoo.kuramqtt.common.PubSubConfig;

/**
 * @author bwoo
 *
 */
public class NullConfig extends PubSubConfig
{

	protected NullConfig(
			String accountId, 
			String requestId, 
			String requesterClientId, 
			String targetClientId)
	{
		super(accountId, requestId, requesterClientId, targetClientId);
	}

	/* (non-Javadoc)
	 * @see com.obs.bwoo.kuramqtt.common.PubSubConfig#getPayload()
	 */
	@Override
	public Map<String, Object> getPayload()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see com.obs.bwoo.kuramqtt.common.PubSubConfig#getPublishChannel()
	 */
	@Override
	public String getPublishChannel()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see com.obs.bwoo.kuramqtt.common.PubSubConfig#getSubscribeChannels()
	 */
	@Override
	public List<String> getSubscribeChannels()
	{
		return null;
	}

}
