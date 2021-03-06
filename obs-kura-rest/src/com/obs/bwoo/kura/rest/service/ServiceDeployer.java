/**
 * 
 */
package com.obs.bwoo.kura.rest.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.server.CommandResult;
import com.obs.bwoo.kuramqtt.server.KuraServerCommand;
import com.obs.bwoo.kuramqtt.server.commands.ServerExecutable;
import com.obs.bwoo.kuramqtt.server.config.InstallConfig;

/**
 * @author bwoo
 *
 */
@ApplicationPath("/rest")
@Path("/deploy")
public class ServiceDeployer
{	
		
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public CommandResult deployPackage(
			@QueryParam("gateway") final String gateway, 
			@QueryParam("pkgUrl") final String pkgUrl,
			@QueryParam("pkgName") final String pkgName,
			@QueryParam("pkgVersion") final String pkgVersion,
			@QueryParam("requester") final String requesterClientId,
			@QueryParam("requestId") final String requestId)
	{
					

		try
		{			
			if ((null == gateway) || 
				(null == requesterClientId) || 
				(null == requestId) ||
				(null == gateway) ||
				(null == pkgUrl) ||
				(null == pkgName) ||
				(null == pkgVersion))
			{
					throw new IllegalArgumentException("Missing one or more parameters: " + 
												" gateway=" + gateway +
												" requester=" + requesterClientId +
												" requestId=" + requestId + 
												" gateway=" + gateway + 
												" pkgUrl=" + pkgUrl + 
												" pkgName=" + pkgName);
				}			
			
			final KuraMqttClient client = new KuraMqttClient(
					ServerConfig.MQTT_SERVER, 
					ServerConfig.MQTT_PORT,
					requesterClientId, 
					ServerConfig.MQTT_USERNAME, 
					ServerConfig.MQTT_PASSWORD);
			
			Map<String, Object> extraConfig = new HashMap<String, Object>();
			extraConfig.put(InstallConfig.PKG_NAME, pkgName);
			extraConfig.put(InstallConfig.PKG_URL, pkgUrl);
			extraConfig.put(InstallConfig.PKG_VERSION, pkgVersion);
			
			// now get all the information ready to publish.
			ServerExecutable executable = KuraServerCommand.getExecutable(
					client, 
					KuraServerCommand.ApiType.INSTALL, 
					ServerConfig.KURA_ACCOUNT_ID, 
					requestId, 
					requesterClientId, 
					gateway,
					extraConfig); 
			
			
			CommandResult result = executable.execute();
						
			//return "hi post " + result.getStatusCode();
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
