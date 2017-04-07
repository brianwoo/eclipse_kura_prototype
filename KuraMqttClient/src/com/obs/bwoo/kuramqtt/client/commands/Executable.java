/**
 * 
 */
package com.obs.bwoo.kuramqtt.client.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.amitinside.mqtt.client.adapter.MessageListener;
import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.obs.bwoo.kuramqtt.client.KuraMqttClient;
import com.obs.bwoo.kuramqtt.client.configs.PubSubConfigs;

/**
 * @author bwoo
 *
 */
public abstract class Executable
{
	private KuraMqttClient client;
	private PubSubConfigs commandConfigs; 
	protected Properties additionalArgs;
	
	//protected String requestId;
	//protected String requesterClientId;
	//protected String publishChannel;
	//protected List<String> subscribeChannels;
	//protected Map<String, Object> metricMap = new HashMap<>(); 
	
	public Executable(
			KuraMqttClient client,
			PubSubConfigs commandConfigs)
	{
		this.client = client;
		this.commandConfigs = commandConfigs;
		this.additionalArgs = new Properties();
	}
	
	
	public Executable(
			KuraMqttClient client,
			PubSubConfigs commandConfigs,
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
	
	
	
	public void execute()
	{
		String pubChannel = getPublishChannel();
		KuraPayload payload = getPublishPayload();
		List<String> subscribeChannels = getSubscribeChannels();

		for (String subChannel : subscribeChannels)
		{
			System.out.println("client = " + client);

			client.subscribe(subChannel, new MessageListener()
			{
				@Override
				public void processMessage(KuraPayload payload)
				{
					System.out.println("#### Received Metrics ####");
					for (String metricName : payload.metricNames())
					{
						System.out.println(metricName + " = " + payload.getMetric(metricName));
					}
				}
			});
		}
				
		client.publish(pubChannel, payload);
	}
	
}
