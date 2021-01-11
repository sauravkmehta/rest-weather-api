package com.saurav.restweatherapi.domain;

import java.io.Serializable;

/**
 * {@link ErrorMessageData} will publish the error message to teh web client.
 * 
 * @author Saurav.
 *
 */
public class ErrorMessageData implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String message;

	public ErrorMessageData(final String message) {
		this.message = message;
	}

	public final String getMessage() {
		return message;
	}
}
