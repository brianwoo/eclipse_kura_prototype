/**
 * 
 */
package com.obs.bwoo.kuramqtt.server.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.amitinside.mqtt.client.adapter.MessageListener;
import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.common.PubSubConfig;
import com.obs.bwoo.kuramqtt.server.exceptions.ConnectFailureException;

/**
 * @author bwoo
 *
 */
public abstract class ServerExecutable
{
	private static final int MAX_NUM_TRIES = 10;
	
	protected KuraMqttClient client;
	protected PubSubConfig commandConfigs; 
	protected Properties additionalArgs;
	
	public ServerExecutable(
			KuraMqttClient client,
			PubSubConfig commandConfigs)
	{
		this.client = client;
		this.commandConfigs = commandConfigs;
		this.additionalArgs = new Properties();
	}
	
	
	public ServerExecutable(
			KuraMqttClient client,
			PubSubConfig commandConfigs,
			Properties additionalArgs)
	{
		this.client = client;
		this.commandConfigs = commandConfigs;
		this.additionalArgs = additionalArgs;
	}
	
		
	public String getPublishChannel()
	{
		return commandConfigs.getPublishChannel();
	}
	
	public List<String> getSubscribeChannels()
	{
		return commandConfigs.getSubscribeChannels();
	}
	
	
	
	public KuraPayload getPublishPayload()
	{
		KuraPayload payload = new KuraPayload();
		
		payload.addMetric("request.id", commandConfigs.getRequestId());
		payload.addMetric("requester.client.id", commandConfigs.getRequesterClientId());
		
		Map<String, Object> metricMap = commandConfigs.getPayload();
		
		for (String key: metricMap.keySet())
		{
			payload.addMetric(key, metricMap.get(key));
		}
		
		return payload;
	}
	
	/**
	 * Connect to mqtt server
	 * 
	 * @return
	 */
	protected void connect() throws ConnectFailureException
	{
		try
		{
			int numOfTries = 0;
			
			client.connect();
			while (!client.isConnected() && numOfTries < MAX_NUM_TRIES)
			{
				System.out.println("Trying to connect... " + numOfTries + "/" + MAX_NUM_TRIES);
				Thread.sleep(1000);
				numOfTries++;
			}
		}
		catch (Exception e)
		{
			if (client != null)
				client.disconnect();
			
			throw new ConnectFailureException("Unable to connect", e);
		}

	}
	
	
	public abstract CommandResult execute();
	
}
