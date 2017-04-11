/**
 * 
 */
package com.obs.bwoo.kuramqtt.cli;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amitinside.mqtt.client.adapter.MessageListener;
import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.obs.bwoo.kuramqtt.cli.commands.Executable;
import com.obs.bwoo.kuramqtt.cli.exceptions.NoPropertiesFoundException;
import com.obs.bwoo.kuramqtt.common.KuraCommand;
import com.obs.bwoo.kuramqtt.common.KuraMqttClient;
import com.obs.bwoo.kuramqtt.common.PubSubConfig;

/**
 * @author bwoo
 *
 */
public class Main {

	private static final int MIN_NUM_ARGS = 1;
	
	
	public static Properties getProperties(String fileLocation)
	{
		InputStream input = null;
		
		try
		{
			String propFile = fileLocation;
			input = new FileInputStream(propFile);
		
			Properties prop = new Properties();
			prop.load(input);
			
			return prop;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new NoPropertiesFoundException("Unable to open file: " + fileLocation, e);
		}
		finally
		{
			if (input != null)
			{
				try 
				{
					input.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
					throw new NoPropertiesFoundException("Unable to close file: " + fileLocation, e);
				}
			}
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length < MIN_NUM_ARGS)
		{
			System.err.println("Usage: Main propertiesFile");
			return;
		}
		
		Properties prop = getProperties(args[0]);
		
		String mqttServerAddr = prop.getProperty("mqttServerAddr");
		String mqttServerPort = prop.getProperty("mqttServerPort");
		String accountId = prop.getProperty("accountId");
		String targetClientId = prop.getProperty("targetClientId");
		String command = prop.getProperty("command");
		
		String requesterClientId = prop.getProperty("requesterClientId");
		String requestId = prop.getProperty("requestId");
				
		
		final KuraMqttClient client = new KuraMqttClient(mqttServerAddr, 
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
			Executable executable = KuraCommand.getExecutable(
					client, 
					command, 
					accountId, 
					requestId, 
					requesterClientId, 
					targetClientId,
					prop);
			
			
			executable.execute();
						
			System.out.println("Press anykey to exit...");
			System.in.read();
			
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
