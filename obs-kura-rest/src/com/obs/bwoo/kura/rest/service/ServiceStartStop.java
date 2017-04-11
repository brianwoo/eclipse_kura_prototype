/**
 * 
 */
package com.obs.bwoo.kura.rest.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.obs.bwoo.kuramqtt.common.KuraCommand;
import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.common.KuraCommand.ApiType;
import com.obs.bwoo.kuramqtt.server.commands.CommandResult;
import com.obs.bwoo.kuramqtt.server.commands.ServerExecutable;
import com.obs.bwoo.kuramqtt.server.config.InstallConfig;
import com.obs.bwoo.kuramqtt.server.config.ServiceStopConfig;

/**
 * @author bwoo
 *
 */
@ApplicationPath("/rest")
@Path("/service")
public class ServiceStartStop
{	
		
	
	private CommandResult service(
			ApiType type, 
			String bundleId, 
			String gateway, 
			String requesterClientId, 
			String requestId)
	{
		final KuraMqttClient client = new KuraMqttClient(
				ServerConfig.MQTT_SERVER, 
				ServerConfig.MQTT_PORT,
				requesterClientId, 
				ServerConfig.MQTT_USERNAME, 
				ServerConfig.MQTT_PASSWORD);
			
		Map<String, Object> extraConfig = new HashMap<String, Object>();
		extraConfig.put(ServiceStopConfig.BUNDLE_ID, bundleId);
		
		// now get all the information ready to publish.
		ServerExecutable executable = KuraCommand.getExecutable(
				client, 
				type, 
				ServerConfig.KURA_ACCOUNT_ID, 
				requestId, 
				requesterClientId, 
				gateway,
				extraConfig); 
		
		
		CommandResult result = executable.execute();
					
		return result;
	}
	
	
	
	@POST
	@Path("{bundleId}/stop")
	@Produces(MediaType.APPLICATION_JSON)
	public CommandResult serviceStop(
			@PathParam("bundleId") final String bundleId,
			@QueryParam("gateway") final String gateway, 
			@QueryParam("requester") final String requesterClientId,
			@QueryParam("requestId") final String requestId)
	{
							
		final KuraMqttClient client = new KuraMqttClient(
				ServerConfig.MQTT_SERVER, 
				ServerConfig.MQTT_PORT,
				requesterClientId, 
				ServerConfig.MQTT_USERNAME, 
				ServerConfig.MQTT_PASSWORD);
		try
		{			
			CommandResult result = service(ApiType.SERVICE_STOP, 
											bundleId, 
											gateway, 
											requesterClientId, 
											requestId);
			return result;
		}
		catch (Exception e)
		{
			System.err.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
			//return "not working";
			CommandResult result = new CommandResult();
			result.setStatusCode(false);
			result.setStatusMsg(e.getMessage());
			return result;
		}
		
	}
	
	
	@POST
	@Path("{bundleId}/start")
	@Produces(MediaType.APPLICATION_JSON)
	public CommandResult serviceStart(
			@PathParam("bundleId") final String bundleId,
			@QueryParam("gateway") final String gateway, 
			@QueryParam("requester") final String requesterClientId,
			@QueryParam("requestId") final String requestId)
	{
							
		final KuraMqttClient client = new KuraMqttClient(
				ServerConfig.MQTT_SERVER, 
				ServerConfig.MQTT_PORT,
				requesterClientId, 
				ServerConfig.MQTT_USERNAME, 
				ServerConfig.MQTT_PASSWORD);
		try
		{			
			CommandResult result = service(ApiType.SERVICE_START, 
											bundleId, 
											gateway, 
											requesterClientId, 
											requestId);
			return result;
		}
		catch (Exception e)
		{
			System.err.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
			//return "not working";
			CommandResult result = new CommandResult();
			result.setStatusCode(false);
			result.setStatusMsg(e.getMessage());
			return result;
		}
		
	}
}
