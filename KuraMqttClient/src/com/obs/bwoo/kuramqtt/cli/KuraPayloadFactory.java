/**
 * 
 */
package com.obs.bwoo.kuramqtt.cli;

import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.obs.bwoo.kuramqtt.common.KuraCommand;
import com.obs.bwoo.kuramqtt.common.KuraCommand.Type;

/**
 * @author bwoo
 *
 */
public class KuraPayloadFactory {

	private String requestId;
	private String requesterClientId;
	
	public KuraPayloadFactory(String requestId, String requesterClientId)
	{
		this.requesterClientId = requesterClientId;
		this.requestId = requestId;
	}

	
	public KuraPayload getPayload(Type type)
	{
		KuraPayload payload = new KuraPayload();
		payload.addMetric("request.id", requestId);
		payload.addMetric("requester.client.id", requesterClientId);
		
		if (Type.DOWNLOAD_1 == type)
		{
			payload.addMetric("job.id", new Long(12345));
			payload.addMetric("dp.uri", "https://github.com/brianwoo/eclipse_kura_prototype/raw/master/com.obs.bwoo.temperature.reader/resources/obsBwooTemperatureReader_reg_strings.dp");
			payload.addMetric("dp.name", "obsBwooTemperatureReader");
			payload.addMetric("dp.version", "1.0.2");
			payload.addMetric("dp.download.protocol", "HTTP");
			payload.addMetric("dp.install.system.update", new Boolean(false));
		}
		else if (Type.DOWNLOAD_2 == type)
		{
			payload.addMetric("job.id", new Long(12345));
			payload.addMetric("dp.uri", "https://github.com/brianwoo/eclipse_kura_prototype/raw/master/com.obs.bwoo.temperature.reader/resources/obsBwooTemperatureReader_json.dp");
			payload.addMetric("dp.name", "obsBwooTemperatureReader");
			payload.addMetric("dp.version", "1.0.3");
			payload.addMetric("dp.download.protocol", "HTTP");
			payload.addMetric("dp.install.system.update", new Boolean(false));
		}		
		else if (Type.SERVICE_START == type)
		{
			// no extra metric needed in payload
		}
		else if (Type.SERVICE_STOP == type)
		{
			// no extra metric needed in payload
		}
		
		return payload;
	}
	
	
}
