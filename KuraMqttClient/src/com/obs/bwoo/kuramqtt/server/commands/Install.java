/**
 * 
 */
package com.obs.bwoo.kuramqtt.server.commands;

import java.util.List;
import java.util.Map;

import com.amitinside.mqtt.client.adapter.MessageListener;
import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.obs.bwoo.kuramqtt.cli.commands.Executable;
import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.common.PubSubConfig;
import com.obs.bwoo.kuramqtt.server.exceptions.ConnectFailureException;

/**
 * @author bwoo
 *
 */
public class Install extends ServerExecutable
{
	
	private static final String KEY_JOB_ID = "job.id";
	private static final String KEY_DL_STATUS = "dp.download.status";
	private static final String KEY_CLIENT_ID = "client.id";
	
	private static final String DL_STATUS_COMPLETED = "COMPLETED";
	private static final String DL_STATUS_IN_PROGRESS = "IN_PROGRESS";
	
	private static final long EXPECTED_TIME_TO_FINISH = 30 * 1000;  // 30 secs to finish
	private static final long SLEEP_TIME_TO_RETRY = 1000;  // 1 sec to sleep
	
	private boolean isInstallDone = false;
		
	public Install(
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
					
					String status = (String) payload.getMetric(KEY_DL_STATUS);
					if (status.equals(DL_STATUS_COMPLETED))
					{
						System.out.println("#### Installation Done ####");
						isInstallDone = true;
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
			
			while (!isInstallDone && System.currentTimeMillis() <= targetTimeToFinish)
			{
				System.out.println("Not done, wait...");
				Thread.sleep(SLEEP_TIME_TO_RETRY);
			}
			
			result.setStatusCode(isInstallDone);
			result.setStatusMsg("Success");
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
