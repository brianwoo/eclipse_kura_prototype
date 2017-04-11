/**
 * 
 */
package com.obs.bwoo.kuramqtt.cli.commands;

import java.util.List;
import java.util.Map;

import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.common.PubSubConfig;

/**
 * @author bwoo
 *
 */
public class Download extends Executable
{
		
	public Download(
			KuraMqttClient client,
			PubSubConfig configs) 
	{
		super(client, configs);
	}
	
	
}
