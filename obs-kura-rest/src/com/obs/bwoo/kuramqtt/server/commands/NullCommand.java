/**
 * 
 */
package com.obs.bwoo.kuramqtt.server.commands;

import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.common.PubSubConfig;
import com.obs.bwoo.kuramqtt.server.CommandResult;

/**
 * @author bwoo
 *
 */
public class NullCommand extends ServerExecutable
{

	public NullCommand(KuraMqttClient client, PubSubConfig commandConfigs)
	{
		super(client, commandConfigs);
		
		System.out.println("##### THIS IS A DUMMY COMMAND, check your properties file to make sure "
				+ "the command matches the commands listed in KuraCommand.java #####");
	}

	
	@Override
	public CommandResult execute()
	{
		CommandResult result = new CommandResult();
		result.setStatusCode(false);
		result.setStatusMsg("##### THIS IS A DUMMY COMMAND, check your properties file to make sure "
				+ "the command matches the commands listed in KuraCommand.java #####");
		
		return result;
	}

}
