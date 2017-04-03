/**
 * 
 */
package com.obs.kafka.producer.exceptions;

/**
 * @author bwoo
 *
 */
public class MsgProduceException extends RuntimeException
{
	
	private static final long serialVersionUID = 8281431706756515183L;

	public MsgProduceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MsgProduceException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public MsgProduceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MsgProduceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public MsgProduceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
