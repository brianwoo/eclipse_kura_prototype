/**
 * 
 */
package com.obs.bwoo.kuramqtt.server.commands;

import java.util.List;
import java.util.Map;

import javax.json.Json;

import com.amitinside.mqtt.client.adapter.MessageListener;
import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.obs.bwoo.kuramqtt.cli.commands.Executable;
import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.common.PubSubConfig;
import com.obs.bwoo.kuramqtt.server.CommandResult;
import com.obs.bwoo.kuramqtt.server.exceptions.ConnectFailureException;
import com.obs.bwoo.kuramqtt.server.utils.JsonUtils;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

/**
 * @author bwoo
 *
 */
public class ServiceListing extends ServerExecutable
{
	
	private static final String KEY_RESPONSE_CODE = "response.code";
	private static final int VAL_RESPONSE_CODE = 200;

	
	private static final long EXPECTED_TIME_TO_FINISH = 10 * 1000;  // 30 secs to finish
	private static final long SLEEP_TIME_TO_RETRY = 1000;  // 1 sec to sleep
	
	private boolean isOperationDone = false;
	private String body = "";
		
	public ServiceListing(
			KuraMqttClient client,
			PubSubConfig configs) 
	{
		super(client, configs);
	}

	
	/**
	 * Setup Subscription to get results.
	 */
	private void setupSubscribe()
	{
		List<String> subscribeChannels = getSubscribeChannels();
		
		for (String subChannel : subscribeChannels)
		{
			//System.out.println("client = " + client);
			
			client.subscribe(subChannel, new MessageListener()
			{
				
				@Override
				public void processMessage(KuraPayload payload)
				{
					
					System.out.println("#### Received Metrics ####");
					for (String metricName : payload.metricNames())
					{
						System.out.println(metricName + " = " + payload.getMetric(metricName));
					}
					
					int status = (Integer) payload.getMetric(KEY_RESPONSE_CODE);
					if (VAL_RESPONSE_CODE == status)
					{
						System.out.println("#### Service Listing Done ####");
						byte[] bodyBytes = payload.getBody();
						body = new String(bodyBytes);
						
						System.out.println("body = " + body);
						isOperationDone = true;
					}
					
				}
			});
		}
	}
	
	
	@Override
	public CommandResult execute()
	{
		String pubChannel = getPublishChannel();
		KuraPayload payload = getPublishPayload();
		
		CommandResult result = new CommandResult();
		
		try
		{
			connect();
			setupSubscribe();
			
			long targetTimeToFinish = System.currentTimeMillis() + EXPECTED_TIME_TO_FINISH;
			client.publish(pubChannel, payload);
			
			while (!isOperationDone && System.currentTimeMillis() <= targetTimeToFinish)
			{
				System.out.println("Not done, wait...");
				Thread.sleep(SLEEP_TIME_TO_RETRY);
			}
						
			// convert the xml to json
			String bodyJson = JsonUtils.fromXml(this.body).toString();
			
			result.setStatusCode(isOperationDone);
			result.setStatusMsg("Success");
			result.setBody(bodyJson);
			return result;
		}
		catch (ConnectFailureException e)
		{
			e.printStackTrace();
			result.setStatusCode(false);
			result.setStatusMsg(e.getMessage());
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result.setStatusCode(false);
			result.setStatusMsg(e.getMessage());
			return result;
		}
		finally
		{
			client.disconnect();
		}
		
		
	}
	
}
