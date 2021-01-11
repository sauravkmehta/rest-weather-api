package com.saurav.restweatherapi.exception;

/**
 * {@link InvalidRequestParameterException} is an unchecked exception which will
 * be thrown if user provide invalid value for nay request parameter.
 * 
 * @author Saurav
 *
 */
public class InvalidRequestParameterException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidRequestParameterException(final String message) {
		super(message);
	}
}
