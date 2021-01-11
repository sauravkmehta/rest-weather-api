package com.saurav.restweatherapi.exception;

/**
 * {@link InvalidCityNameException} is an unchecked exception which will be
 * thrown if city name does not exist for which we are retrieving weather data.
 * 
 * @author Saurav
 *
 */
public class InvalidCityNameException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidCityNameException(final String message) {
		super(message);
	}
}
