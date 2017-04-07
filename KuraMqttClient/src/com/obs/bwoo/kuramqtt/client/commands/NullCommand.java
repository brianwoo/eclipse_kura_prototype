/**
 * 
 */
package com.obs.bwoo.kuramqtt.client.commands;

import com.obs.bwoo.kuramqtt.client.KuraMqttClient;
import com.obs.bwoo.kuramqtt.client.configs.PubSubConfigs;

/**
 * @author bwoo
 *
 */
public class NullCommand extends Executable
{

	public NullCommand(KuraMqttClient client, PubSubConfigs commandConfigs)
	{
		super(client, commandConfigs);
		
		System.out.println("##### THIS IS A DUMMY COMMAND, check your properties file to make sure "
				+ "the command matches the commands listed in KuraCommand.java #####");
	}

}
