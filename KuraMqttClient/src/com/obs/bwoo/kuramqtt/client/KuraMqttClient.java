/**
 * 
 */
package com.obs.bwoo.kuramqtt.client;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import com.amitinside.mqtt.client.adapter.MessageListener;
import com.amitinside.mqtt.client.kura.message.KuraPayload;
import com.amitinside.mqtt.client.kura.message.payload.operator.KuraPayloadDecoder;
import com.amitinside.mqtt.client.kura.message.payload.operator.KuraPayloadEncoder;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

/**
 * @author bwoo
 *
 */
public class KuraMqttClient 
{
	
	public static final String PROTOCOL = "tcp";
	

	private String host;
	private String port;
	private String clientId;
	private String username;
	private String password;
	private ReentrantLock connectionLock;
	private boolean isConnected;

	private String errorMsg;
	
	protected Map<String, MessageListener> channels = null;


	protected CallbackConnection connection = null;


	public KuraMqttClient(
			final String host, 
			final String port, 
			final String clientId, 
			final String username,
			final String password) 
	{
		this.host = host;
		this.port = port;
		this.clientId = clientId;
		this.username = username;
		this.password = password;
		this.connectionLock = new ReentrantLock();
	}
	
	/**
	 * Returns the MQTT URI Scheme
	 */
	private String hostToURI(final String host, final String port) {
		return PROTOCOL + "://" + host + ":" + port;
	}

	public boolean isConnected() {
		return isConnected;
	}	
	
	public boolean connect() 
	{
		checkNotNull(this.host);
		checkNotNull(this.port);
		checkNotNull(this.clientId);

		final MQTT mqtt = new MQTT();

		try {

			mqtt.setHost(this.hostToURI(this.host, this.port));
			mqtt.setClientId(this.clientId);
			mqtt.setPassword(this.password);
			mqtt.setUserName(this.username);

		} catch (final URISyntaxException e) {
			System.err.println("Invalid Host URL");
			System.err.println(Throwables.getStackTraceAsString(e));
		}
		try {
			if (this.connectionLock.tryLock(5, TimeUnit.SECONDS)) {
				this.safelyConnect(mqtt);
			}
			this.isConnected = true;
		} catch (final InterruptedException e) {
			this.isConnected = false;
		} catch (final ConnectionException e) {
			this.isConnected = false;
		} finally {
			this.connectionLock.unlock();
		}
		return this.isConnected;
	}
	
	/**
	 * Disconnects the client in a thread safe way
	 */
	private void safelyDisconnect() {
		if (this.connection != null) {
			this.connection.disconnect(new Callback<Void>() {
				@Override
				public void onFailure(final Throwable throwable) {
					System.out.println("Error while disconnecting");
				}

				@Override
				public void onSuccess(final Void aVoid) {
					System.out.println("Successfully disconnected");
				}
			});
		}
	}
	
	public void disconnect() {
		try {
			if (this.connectionLock.tryLock(5, TimeUnit.SECONDS)) {
				this.safelyDisconnect();
			}
		} catch (final Exception e) {
			System.out.println("Exception while disconnecting");
		}
	}
	
	/**
	 * Connect in a thread safe manner
	 */
	private void safelyConnect(final MQTT mqtt) throws ConnectionException {
		if (this.isConnected) {
			this.disconnect();
		}
		// Initialize channels
		this.channels = Maps.newHashMap();
		// Register callbacks
		this.connection = mqtt.callbackConnection();
		this.connection.listener(new Listener() {
			@Override
			public void onConnected() {
				System.out.println("Host connected");
			}

			@Override
			public void onDisconnected() {
				System.out.println("Host disconnected");
			}

			@Override
			public void onFailure(final Throwable throwable) {
				System.out.println("Exception Occurred: " + throwable.getMessage());
			}

			@Override
			public void onPublish(final UTF8Buffer mqttChannel, final Buffer mqttMessage, final Runnable ack) {
				if (KuraMqttClient.this.channels.containsKey(mqttChannel.toString())) {
					final KuraPayloadDecoder decoder = new KuraPayloadDecoder(mqttMessage.toByteArray());

					try {
						KuraMqttClient.this.channels.get(mqttChannel.toString())
								.processMessage(decoder.buildFromByteArray());
					} catch (final IOException e) {
						System.out.println("I/O Exception Occurred: " + e.getMessage());
					}
				}
				ack.run();
			}
		});
		// Connect to broker in a blocking fashion
		final CountDownLatch l = new CountDownLatch(1);
		this.connection.connect(new Callback<Void>() {
			@Override
			public void onFailure(final Throwable throwable) {
				KuraMqttClient.this.errorMsg = "Impossible to CONNECT to the MQTT server, terminating";
				System.out.println(KuraMqttClient.this.errorMsg);
			}

			@Override
			public void onSuccess(final Void aVoid) {
				l.countDown();
				System.out.println("Successfully Connected to Host");
			}

		});
		try {
			if (!l.await(5, TimeUnit.SECONDS)) {
				this.errorMsg = "Impossible to CONNECT to the MQTT server: TIMEOUT. Terminating";
				System.out.println(this.errorMsg);
				this.exceptionOccurred(this.errorMsg);
			}
		} catch (final InterruptedException e) {
			this.errorMsg = "\"Impossible to CONNECT to the MQTT server, terminating\"";
			System.out.println(this.errorMsg);
			this.exceptionOccurred(this.errorMsg);
		}
	}
	
	/**
	 * Connection Exception triggerer
	 */
	private void exceptionOccurred(final String message) throws ConnectionException {
		throw new ConnectionException(message);
	}
	
	
	public void publish(final String channel, final KuraPayload payload) {
		if (this.connection != null) {
			final KuraPayloadEncoder encoder = new KuraPayloadEncoder(payload);
			try {
				this.connection.publish(channel, encoder.getBytes(), QoS.AT_MOST_ONCE, false, new Callback<Void>() {
					@Override
					public void onFailure(final Throwable throwable) {
						System.out.println("Impossible to publish message to channel " + channel);
					}

					@Override
					public void onSuccess(final Void aVoid) {
						System.out.println("Successfully published");
					}
				});
			} catch (final IOException e) {
				System.out.println("I/O Exception Occurred: " + e.getMessage());
			}
		}
	}
	
	
	public void subscribe(final String channel, final MessageListener callback) {
		if (this.connection != null) {
			if (this.channels.containsKey(channel)) {
				return;
			}
			final CountDownLatch l = new CountDownLatch(1);
			final Topic[] topic = { new Topic(channel, QoS.AT_MOST_ONCE) };
			this.connection.subscribe(topic, new Callback<byte[]>() {
				@Override
				public void onFailure(final Throwable throwable) {
					System.out.println("Impossible to SUBSCRIBE to channel \"" + channel + "\"");
					l.countDown();
				}

				@Override
				public void onSuccess(final byte[] bytes) {
					KuraMqttClient.this.channels.put(channel, callback);
					l.countDown();
					System.out.println("Successfully subscribed to " + channel);
				}
			});
			try {
				l.await();
			} catch (final InterruptedException e) {
				System.out.println("Impossible to SUBSCRIBE to channel \"" + channel + "\"");
			}
		}
	}	
	
	

	
	
	/**
	 * Connection related exception
	 */
	class ConnectionException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ConnectionException(final String message) {
			super(message);
		}
	}

	
	
	
}
