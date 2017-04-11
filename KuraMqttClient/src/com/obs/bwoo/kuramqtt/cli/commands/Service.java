/**
 * 
 */
package com.obs.bwoo.kuramqtt.cli.commands;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.common.PubSubConfig;

/**
 * @author bwoo
 *
 */
public class Service extends Executable
{
		
	public Service(
			KuraMqttClient client,
			PubSubConfig configs,
			Properties additionalArgs) 
	{
		super(client, configs, additionalArgs);
	}

	
	@Override
	public String getPublishChannel()
	{
		String publishChannel = super.getPublishChannel();
		String bundleId = (String) super.additionalArgs.get("bundleId");
		
		return publishChannel + bundleId;
	}
	
	
	
}
