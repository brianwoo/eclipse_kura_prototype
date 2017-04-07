/**
 * 
 */
package com.obs.bwoo.kuramqtt.start;

import java.util.Properties;

import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.obs.bwoo.kuramqtt.client.KuraMqttClient;
import com.obs.bwoo.kuramqtt.client.commands.Download;
import com.obs.bwoo.kuramqtt.client.commands.Executable;
import com.obs.bwoo.kuramqtt.client.commands.Service;
import com.obs.bwoo.kuramqtt.client.configs.Download1Configs;
import com.obs.bwoo.kuramqtt.client.configs.Download2Configs;
import com.obs.bwoo.kuramqtt.client.configs.PubSubConfigs;
import com.obs.bwoo.kuramqtt.client.configs.ServiceStopConfigs;
import com.obs.bwoo.kuramqtt.client.configs.ServiceStartConfigs;

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
	
	private Type commandType;
	
	private PubSubConfigs configs;
	

	
	
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
			PubSubConfigs configs = 
					new Download1Configs(accountId, requestId, requesterClientId, targetClientId);
			
			return new Download(client, configs);
		}
			
		else if (command.equals("download2"))
		{
			PubSubConfigs configs = 
					new Download2Configs(accountId, requestId, requesterClientId, targetClientId);
			
			return new Download(client, configs);
			
		}
		else if (command.equals("serviceStart"))
		{
			PubSubConfigs configs = 
					new ServiceStartConfigs(accountId, requestId, requesterClientId, targetClientId);
			
			return new Service(client, configs, additionalArgs);
			
		}
			//commandType = Type.SERVICE_START;
		
		else if (command.equals("serviceStop"))
		{
			PubSubConfigs configs = 
					new ServiceStopConfigs(accountId, requestId, requesterClientId, targetClientId);
			
			return new Service(client, configs, additionalArgs);
		}
			
		return null;
	}
	
	
	/*
	public String getChannel()
	{
		
		
		KuraChannelFactory factory = new KuraChannelFactory(accountId, targetClientId);
		
		String channel = factory.getChannel(commandType);
		return channel;
	}
	
	
	public KuraPayload getPayload(String requestId, String requesterClientId)
	{
		KuraPayloadFactory factory = new KuraPayloadFactory(requestId, requesterClientId);
		
		KuraPayload payload = factory.getPayload(commandType);
		return payload;
	}
	*/

}
