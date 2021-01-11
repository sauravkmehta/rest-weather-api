package com.saurav.restweatherapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.saurav.restweatherapi.domain.ErrorMessageData;

/**
 * {@link WeatherAppExceptionHandler} is of {@link ExceptionHandler} to handle
 * different exception in application. This will also create a
 * {@link ErrorMessageData} to return error message.
 * 
 * @author Saurav
 *
 */
@ControllerAdvice
public class WeatherAppExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherAppExceptionHandler.class);

	@ExceptionHandler({ InvalidRequestParameterException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorMessageData invalidOptionException(final InvalidRequestParameterException e) {
		LOGGER.error(e.getMessage(), e);
		return new ErrorMessageData(e.getMessage());
	}

	@ExceptionHandler({ InvalidCityNameException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorMessageData invalidCityNameException(final InvalidCityNameException e) {
		LOGGER.error(e.getMessage(), e);
		return new ErrorMessageData(e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorMessageData internalServerError(final Exception e) {
		LOGGER.error(e.getMessage(), e);
		return new ErrorMessageData(e.getMessage());
	}
}
