/**
 * 
 */
package com.obs.bwoo.kuramqtt.common;

import java.util.Map;
import java.util.Properties;

import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.obs.bwoo.kuramqtt.cli.commands.Download;
import com.obs.bwoo.kuramqtt.cli.commands.Executable;
import com.obs.bwoo.kuramqtt.cli.commands.NullCommand;
import com.obs.bwoo.kuramqtt.cli.commands.Service;
import com.obs.bwoo.kuramqtt.cli.config.Download1Config;
import com.obs.bwoo.kuramqtt.cli.config.Download2Config;
import com.obs.bwoo.kuramqtt.cli.config.NullConfig;
import com.obs.bwoo.kuramqtt.cli.config.ServiceStartConfig;
import com.obs.bwoo.kuramqtt.cli.config.ServiceStopConfig;

/**
 * @author bwoo
 *
 */
public class KuraCommand {

	public enum Type {
		
		DOWNLOAD_1,
		DOWNLOAD_2,
		SERVICE_START,
		SERVICE_STOP
	}
	
	
	public enum ApiType {
		INSTALL,
		SERVICE_START,
		SERVICE_STOP
	}
	
	private Type commandType;
	
	private PubSubConfig configs;
	

	
	/** 
	 * This is used by the CLI interface only.
	 * 
	 * @param client
	 * @param command
	 * @param accountId
	 * @param requestId
	 * @param requesterClientId
	 * @param targetClientId
	 * @param additionalArgs
	 * @return
	 */
	public static Executable getExecutable(
			KuraMqttClient client,
			String command, 
			String accountId, 
			String requestId,
			String requesterClientId, 
			String targetClientId,
			Properties additionalArgs)
	{
		if (command.equals("download1"))
		{
			PubSubConfig configs = 
					new Download1Config(accountId, requestId, requesterClientId, targetClientId);
			
			return new Download(client, configs);
		}
			
		else if (command.equals("download2"))
		{
			PubSubConfig configs = 
					new Download2Config(accountId, requestId, requesterClientId, targetClientId);
			
			return new Download(client, configs);
			
		}
		else if (command.equals("serviceStart"))
		{
			PubSubConfig configs = 
					new ServiceStartConfig(accountId, requestId, requesterClientId, targetClientId);
			
			return new Service(client, configs, additionalArgs);
			
		}
			//commandType = Type.SERVICE_START;
		
		else if (command.equals("serviceStop"))
		{
			PubSubConfig configs = 
					new ServiceStopConfig(accountId, requestId, requesterClientId, targetClientId);
			
			return new Service(client, configs, additionalArgs);
		}
		else
		{
			PubSubConfig configs = new NullConfig(accountId, requestId, requesterClientId, targetClientId);
			return new NullCommand(client, configs);
		}
			
		
	}
	
	
	
	
	

}
