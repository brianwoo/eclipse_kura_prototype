/**
 * 
 */
package com.obs.bwoo.kuramqtt.client.commands;

import java.util.List;
import java.util.Map;

import com.obs.bwoo.kuramqtt.client.KuraMqttClient;
import com.obs.bwoo.kuramqtt.client.configs.PubSubConfigs;

/**
 * @author bwoo
 *
 */
public class Download extends Executable
{
		
	public Download(
			KuraMqttClient client,
			PubSubConfigs configs) 
	{
		super(client, configs);
	}
	
	
}
