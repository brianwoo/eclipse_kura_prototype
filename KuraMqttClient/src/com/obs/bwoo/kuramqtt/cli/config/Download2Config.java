/**
 * 
 */
package com.obs.bwoo.kuramqtt.cli.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.obs.bwoo.kuramqtt.common.PubSubConfig;

/**
 * @author bwoo
 *
 */
public class Download2Config extends PubSubConfig
{

	public Download2Config(String accountId, String requestId, String requesterClientId, String targetClientId)
	{
		super(accountId, requestId, requesterClientId, targetClientId);
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
		payloadMap.put("dp.uri", "https://github.com/brianwoo/eclipse_kura_prototype/"
				+ "raw/master/com.obs.bwoo.temperature.reader/"
				+ "resources/obsBwooTemperatureReader_json.dp");
		payloadMap.put("dp.name", "obsBwooTemperatureReader");
		payloadMap.put("dp.version", "1.0.3");
		payloadMap.put("dp.download.protocol", "HTTP");
		payloadMap.put("dp.install.system.update", new Boolean(false));
		
		return payloadMap;
	}
	
}
