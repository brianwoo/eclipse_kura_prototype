/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech
 *******************************************************************************/
package com.obs.bwoo.temperature.reader.component;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.kura.cloud.CloudClient;
import org.eclipse.kura.cloud.CloudClientListener;
import org.eclipse.kura.cloud.CloudService;
import org.eclipse.kura.configuration.ConfigurableComponent;
import org.eclipse.kura.message.KuraPayload;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obs.bwoo.temperature.reader.factory.TemperatureReaderFactory;
import com.obs.bwoo.temperature.reader.interfaces.TemperatureReader;
import com.obs.kafka.producer.SimpleProducer;

public class TemperatureReaderComponent implements ConfigurableComponent
{

	private static final Logger s_logger = LoggerFactory.getLogger(TemperatureReaderComponent.class);

	// Cloud Application identifier
	private static final String APP_ID = "temperature_reader";

	// Publishing Property Names
	private static final String PUBLISH_RATE_PROP_NAME = "publish.rate";
	private static final String PUBLISH_TOPIC_PROP_NAME = "publish.semanticTopic";
	private static final String PUBLISH_SERVER_NAME = "publish.server.name";
	private static final String PUBLISH_SERVER_PORT_NAME = "publish.server.port";

	private final ScheduledExecutorService m_worker;
	private ScheduledFuture<?> m_handle;

	//private float m_temperature;
	private Map<String, Object> m_properties;
	//private final Random m_random;

	private String serverName, serverPort;

	private TemperatureReader temperatureReader;

	// ----------------------------------------------------------------
	//
	// Dependencies
	//
	// ----------------------------------------------------------------

	public TemperatureReaderComponent()
	{
		super();
		//this.m_random = new Random();
		this.m_worker = Executors.newSingleThreadScheduledExecutor();

		TemperatureReaderFactory temperatureReaderFactory = new TemperatureReaderFactory();

		temperatureReader = temperatureReaderFactory.getTemperatureReader(TemperatureReaderFactory.Type.FUEL_TANK);
	}


	// ----------------------------------------------------------------
	//
	// Activation APIs
	//
	// ----------------------------------------------------------------

	protected void activate(ComponentContext componentContext, Map<String, Object> properties)
	{
		s_logger.info("Activating TemperatureReaderComponent...");

		this.m_properties = properties;
		for (String s : properties.keySet())
		{
			s_logger.info("Activate - " + s + ": " + properties.get(s));
		}

		try
		{
			doUpdate(false);
		}
		catch (Exception e)
		{
			s_logger.error("Error during component activation", e);
			throw new ComponentException(e);
		}
		s_logger.info("Activating TemperatureReaderComponent... Done.");
	}

	protected void deactivate(ComponentContext componentContext)
	{
		s_logger.debug("Deactivating TemperatureReaderComponent...");

		// shutting down the worker and cleaning up the properties
		this.m_worker.shutdown();

		// Releasing the CloudApplicationClient
		s_logger.info("Releasing CloudApplicationClient for {}...", APP_ID);
		// this.m_cloudClient.release();

		s_logger.debug("Deactivating TemperatureReaderComponent... Done.");
	}

	public void updated(Map<String, Object> properties)
	{
		s_logger.info("Updated TemperatureReaderComponent...");

		// store the properties received
		this.m_properties = properties;
		for (String s : properties.keySet())
		{
			s_logger.info("Update - " + s + ": " + properties.get(s));
		}

		// try to kick off a new job
		doUpdate(true);
		s_logger.info("Updated TemperatureReaderComponent... Done.");
	}

	// ----------------------------------------------------------------
	//
	// Private Methods
	//
	// ----------------------------------------------------------------

	/**
	 * Called after a new set of properties has been configured on the service
	 */
	private void doUpdate(boolean onUpdate)
	{
		// cancel a current worker handle if one if active
		if (this.m_handle != null)
		{
			this.m_handle.cancel(true);
		}

		// schedule a new worker based on the properties of the service
		int pubrate = (Integer) this.m_properties.get(PUBLISH_RATE_PROP_NAME);
		this.serverName = (String) this.m_properties.get(PUBLISH_SERVER_NAME);
		this.serverPort = (String) this.m_properties.get(PUBLISH_SERVER_PORT_NAME);

		this.m_handle = this.m_worker.scheduleAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				Thread.currentThread().setName(getClass().getSimpleName());

				float temperature = temperatureReader.getTemperature();

				doPublish(temperature);
			}
		}, 0, pubrate, TimeUnit.SECONDS);
	}

	/**
	 * Called at the configured rate to publish the next temperature
	 * measurement.
	 */
	private void doPublish(float temperature)
	{
		// fetch the publishing configuration from the publishing properties
		String topic = (String) this.m_properties.get(PUBLISH_TOPIC_PROP_NAME);
		String url = serverName + ":" + serverPort;

		// Publish the message
		try
		{

			System.out.println("TemperatureReaderComponent send to topic: " + topic);

			System.out.println("created SimpleProducer");

			String currentTemp = "Current Temperature: " + temperature;
			
			//String currentTemp = "{ 'currentTemp': " + temperature + " }";

			SimpleProducer.send(url, 3, topic, currentTemp);

			System.out.println("Published to " + topic);

		}
		catch (Exception e)
		{
			System.out.println("Cannot published to " + topic + " " + e.getMessage());
			// s_logger.error("Cannot publish topic: " + topic, e);
		}
		catch (Throwable e)
		{
			System.out.println("THROWABLE Cannot published to " + topic + " " + e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		SimpleProducer.send("192.168.56.101:9092", 3, "test", "a test message!");
	}
}
