/**
 * 
 */
package com.obs.kafka.producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.Producer;
//import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

//import com.bwoo.dummybundle.DummyClass;
import com.obs.kafka.producer.exceptions.MsgProduceException;

/**
 * @author bwoo
 *
 */
public class SimpleProducer {

	
	public static void send(String bootstrapServers, int retries, String topic, String message)
	{
				
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapServers);
		props.put("acks", "all");
		props.put("retries", retries);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

				
		Producer<String, String> producer = null; 
						
		try
		{
			// Make sure this line is added to avoid the Classloader 
			// issue in osgi environment.
			// https://issues.apache.org/jira/browse/KAFKA-3218
			Thread.currentThread().setContextClassLoader(null);
			
			producer = new KafkaProducer<String, String>(props);
						
			ProducerRecord<String, String> data = 
						new ProducerRecord<String, String>(topic, message);
			
			producer.send(data);

			System.out.println("Message sent successfully");
			
		}
		catch (Exception e)
		{
			System.out.println("send 9");
			
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new MsgProduceException("Error caught from SimpleProducer "
					+ "EXCEPTION", e);
		}
		catch (Throwable e)
		{
			System.out.println("send 9a");
			
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new MsgProduceException("Error caught from SimpleProducer "
					+ "THROWABLE", e);
		}
		finally
		{			
			if (producer != null)
				producer.close();
		}
	}
	
	
	
	public static void main(String[] args) throws Exception 
	{
		SimpleProducer.send("192.168.56.101:9092", 3, "test", "a test message!");
	}
	
	
	
	
	
}
