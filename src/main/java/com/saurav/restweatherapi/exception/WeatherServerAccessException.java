package com.saurav.restweatherapi.exception;

import org.springframework.web.client.HttpServerErrorException.InternalServerError;

/**
 * {@link WeatherServerAccessException} is an unchecked exception which will be thrown if we
 * get any {@link InternalServerError} retrieving weather data.
 * 
 * @author Saurav
 *
 */
public class WeatherServerAccessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public WeatherServerAccessException(final String message) {
		super(message);
	}
}
