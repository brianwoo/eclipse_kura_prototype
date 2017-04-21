/**
 * 
 */
package com.obs.bwoo.kuramqtt.server;

import java.util.Map;

import com.obs.bwoo.kuramqtt.cli.config.NullConfig;
import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.common.PubSubConfig;
import com.obs.bwoo.kuramqtt.server.commands.Install;
import com.obs.bwoo.kuramqtt.server.commands.NullCommand;
import com.obs.bwoo.kuramqtt.server.commands.ServerExecutable;
import com.obs.bwoo.kuramqtt.server.commands.Service;
import com.obs.bwoo.kuramqtt.server.commands.ServiceListing;
import com.obs.bwoo.kuramqtt.server.config.InstallConfig;
import com.obs.bwoo.kuramqtt.server.config.ServiceListingConfig;
import com.obs.bwoo.kuramqtt.server.config.ServiceStartConfig;
import com.obs.bwoo.kuramqtt.server.config.ServiceStopConfig;

/**
 * @author bwoo
 *
 */
public class KuraServerCommand {

	public enum Type {
		
		DOWNLOAD_1,
		DOWNLOAD_2,
		SERVICE_START,
		SERVICE_STOP
	}
	
	
	public enum ApiType {
		INSTALL,
		SERVICE_START,
		SERVICE_STOP,
		SERVICE_LISTING
	}
	
	private Type commandType;
	
	private PubSubConfig configs;
	

	

	
	
	
	
	/**
	 * This is used in the REST API.
	 * 
	 * @param client
	 * @param command
	 * @param accountId
	 * @param requestId
	 * @param requesterClientId
	 * @param targetClientId
	 * @param extraConfig
	 * @return
	 */
	public static ServerExecutable getExecutable(
			KuraMqttClient client,
			ApiType command, 
			String accountId, 
			String requestId,
			String requesterClientId, 
			String targetClientId,
			Map<String, Object> extraConfig)
	{
		
		if (ApiType.INSTALL == command)
		{
			PubSubConfig configs = 
					new InstallConfig(accountId, requestId, requesterClientId, targetClientId, extraConfig);
			
			return new Install(client, configs);
			
		}
		else if (ApiType.SERVICE_STOP == command)
		{
			PubSubConfig configs = 
					new ServiceStopConfig(
							accountId, requestId, requesterClientId, targetClientId, extraConfig);
			
			return new Service(client, configs);
		}	
		else if (ApiType.SERVICE_START == command)
		{
			PubSubConfig configs = 
					new ServiceStartConfig(
							accountId, requestId, requesterClientId, targetClientId, extraConfig);
			
			return new Service(client, configs);
		}	
		else if (ApiType.SERVICE_LISTING == command)
		{
			PubSubConfig configs = 
					new ServiceListingConfig(
							accountId, requestId, requesterClientId, targetClientId);
			
			return new ServiceListing(client, configs);
		}
		else
		{
			PubSubConfig config = 
					new NullConfig(accountId, requestId, requesterClientId, targetClientId);
			return new NullCommand(client, config);
		}
	}
	
	

}
