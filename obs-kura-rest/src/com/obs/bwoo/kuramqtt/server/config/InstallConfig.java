/**
 * 
 */
package com.obs.bwoo.kuramqtt.server.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.obs.bwoo.kuramqtt.common.PubSubConfig;

/**
 * @author bwoo
 *
 */
public class InstallConfig extends PubSubConfig
{
	public static final String PKG_URL = "pkgUrl";
	public static final String PKG_NAME = "pkgName";
	public static final String PKG_VERSION = "pkgVersion";
	
	
	private String pkgUrl, pkgName, pkgVersion;

	public InstallConfig(
			String accountId, 
			String requestId, 
			String requesterClientId, 
			String targetClientId, 
			Map<String, Object> extraConfig)
	{
		super(accountId, requestId, requesterClientId, targetClientId);
		
		this.pkgUrl = (String) extraConfig.get(PKG_URL);
		this.pkgName = (String) extraConfig.get(PKG_NAME);
		this.pkgVersion = (String) extraConfig.get(PKG_VERSION);
	}
	
	
	@Override
	public List<String> getSubscribeChannels()
	{
		List<String> channels = new ArrayList<>();
		channels.add(
				"$EDC/" + 
				accountId + 
				"/" + 
				requesterClientId + 
				"/DEPLOY-V2/NOTIFY/" + 
				targetClientId + 
				"/download");
		
		return channels;
	}

	@Override
	public String getPublishChannel()
	{
		return "$EDC/" + 
				accountId + "/" +
				targetClientId + "/" +
				"DEPLOY-V2/EXEC/download";
	}

	@Override
	public Map<String, Object> getPayload()
	{
		Map<String, Object> payloadMap = new HashMap<>();
				
		payloadMap.put("job.id", new Long(12345));
		payloadMap.put("dp.uri", this.pkgUrl);
		payloadMap.put("dp.name", this.pkgName);
		payloadMap.put("dp.version", this.pkgVersion);
		payloadMap.put("dp.download.protocol", "HTTP");
		payloadMap.put("dp.install.system.update", new Boolean(false));
		
		return payloadMap;
	}
	
}
