/**
 * 
 */
package com.obs.bwoo.kuramqtt.start;

import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.obs.bwoo.kuramqtt.start.KuraCommand.Type;

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
			payload.addMetric("dp.uri", "http://s3-us-west-2.amazonaws.com/dzmtzxbieb/terminator.dp");
			payload.addMetric("dp.name", "terminator");
			payload.addMetric("dp.version", "1.0.0");
			payload.addMetric("dp.download.protocol", "HTTP");
			payload.addMetric("dp.install.system.update", new Boolean(false));
		}
		else if (Type.DOWNLOAD_2 == type)
		{
			payload.addMetric("job.id", new Long(12345));
			payload.addMetric("dp.uri", "http://s3-us-west-2.amazonaws.com/dzmtzxbieb/terminator.dp");
			payload.addMetric("dp.name", "terminator");
			payload.addMetric("dp.version", "1.0.0");
			payload.addMetric("dp.download.protocol", "HTTP");
			payload.addMetric("dp.install.system.update", new Boolean(false));
		}		
		
		return payload;
	}
	
	
}
