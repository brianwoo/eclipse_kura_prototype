/**
 * 
 */
package com.obs.bwoo.kuramqtt.cli.commands;

import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.common.PubSubConfig;

/**
 * @author bwoo
 *
 */
public class NullCommand extends Executable
{

	public NullCommand(KuraMqttClient client, PubSubConfig commandConfigs)
	{
		super(client, commandConfigs);
		
		System.out.println("##### THIS IS A DUMMY COMMAND, check your properties file to make sure "
				+ "the command matches the commands listed in KuraCommand.java #####");
	}

}
