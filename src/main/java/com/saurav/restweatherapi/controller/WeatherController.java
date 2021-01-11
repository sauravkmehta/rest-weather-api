package com.saurav.restweatherapi.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.restweatherapi.domain.WeatherData;
import com.saurav.restweatherapi.exception.InvalidRequestParameterException;
import com.saurav.restweatherapi.service.WeatherProviderApiConsumer;

/**
 * {@link WeatherController} is a spring {@link RestController} controller to handle REST requests towards
 * '/myapp' endpoint.
 * 
 * @author Saurav
 *
 */
@RestController
@RequestMapping("/myapp")
public class WeatherController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);
	private final List<String> validUnits = Arrays.asList("metric", "imperial");

	@Autowired
	@Qualifier("OpenweathermapProviderApiConsumer")
	WeatherProviderApiConsumer apiConsumer;

	@GetMapping("/weather/{cityname}")
	public WeatherData weather(@PathVariable final String cityname,
			@RequestParam(defaultValue = "metric") String units) {
		LOGGER.info("Accessing weather endpoint /weather/{} for application", cityname );
		validateRequestParameter(units);
		final WeatherData result = apiConsumer.getWeatherData(cityname, units);
		return result;
	}

	private void validateRequestParameter(final String units) {
		if (units != null && !validUnits.contains(units)) {
			throw new InvalidRequestParameterException(
					"Invalid value for units field. Only supported units are metric and imperial");
		}
	}
}
