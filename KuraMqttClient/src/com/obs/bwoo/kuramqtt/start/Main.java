/**
 * 
 */
package com.obs.bwoo.kuramqtt.start;

import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.obs.bwoo.kuramqtt.client.KuraMqttClient;

/**
 * @author bwoo
 *
 */
public class Main {

	private static final String DEFAULT_REQUESTER_CLIENT_ID = "obsUser";
	private static final String DEFAULT_REQUEST_ID = "12345";
	private static final int MIN_NUM_ARGS = 5;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length < MIN_NUM_ARGS)
		{
			System.err.println("Usage: Main mqttServerAddr mqttServerPort accountId targetClientId command "
					+ "requesterClientId requestId");
			
			System.err.println("requesterClientId & requestId - auto generated if not entered");
			
			return;
		}
		
		String mqttServerAddr = args[0];
		String mqttServerPort = args[1];
		String accountId = args[2];
		String targetClientId = args[3];
		String command = args[4];
		
		String requesterClientId = DEFAULT_REQUESTER_CLIENT_ID;
		String requestId = DEFAULT_REQUEST_ID;
		
		if (args.length > MIN_NUM_ARGS && args[5] != null) requesterClientId = args[5];
		if (args.length > MIN_NUM_ARGS && args[6] != null) requestId = args[6];
			
		
		
		KuraMqttClient client = new KuraMqttClient(mqttServerAddr, 
												   mqttServerPort, 
												   requesterClientId, 
												   "", 
												   "");
		try
		{
			client.connect();
			while (!client.isConnected())
			{
				System.out.println("Trying to connect...");
				Thread.sleep(1000);
			}
			
			System.out.println("Connected");
			
			// now get all the information ready to publish.
			KuraCommand kCommand = new KuraCommand(command);
			String channel = kCommand.getChannel(accountId, targetClientId);
			KuraPayload payload = kCommand.getPayload(requestId, requesterClientId);
			
			/*
			KuraPayload payload = new KuraPayload();
			payload.addMetric("request.id", "1363603920892-8078887174204257595");
			payload.addMetric("requester.client.id", "requesterClientId");
			payload.addMetric("job.id", new Long(12345));
			payload.addMetric("dp.uri", "http://s3-us-west-2.amazonaws.com/dzmtzxbieb/terminator.dp");
			payload.addMetric("dp.name", "terminator");
			payload.addMetric("dp.version", "1.0.0");
			payload.addMetric("dp.download.protocol", "HTTP");
			payload.addMetric("dp.install.system.update", new Boolean(false));
			
			String channel = "$EDC/brian/08:00:27:52:DC:FC/DEPLOY-V2/EXEC/download";
			*/
			
			client.publish(channel, payload);
			
		}
		catch (Exception e)
		{
			System.err.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		finally 
		{
			client.disconnect();
		}
	}
	

}
