package com.saurav.restweatherapi.service;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.saurav.restweatherapi.domain.WeatherData;
import com.saurav.restweatherapi.exception.InvalidCityNameException;
import com.saurav.restweatherapi.exception.WeatherServerAccessException;

/**
 * {@link OpenweathermapProviderApiConsumer} will interact with
 * 'api.openweathermap.org' website to collect the weather data.
 * 
 * @author Saurav
 *
 */
@Service(value = "OpenweathermapProviderApiConsumer")
public class OpenweathermapProviderApiConsumer extends WeatherProviderApiConsumer {
	private static final String INTERNAL_SERVICE_ERROR_MSG = "Internal service error while retrieving data from the whether provider service.";
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenweathermapProviderApiConsumer.class);

	@Autowired
	private ConfigurationReader configReader;

	@Override
	public WeatherData getWeatherData(final String cityname, final String units) {
		final String url = configReader.getUrl();
		// In application.yml file, appid is stored in base 64 encoded form which need
		// to be decoded before we can use in our application. In real world scenario,
		// we can use encryption key or store this confidential data in vaults.
		final String appid = getBase64DecodedAppId(configReader.getAppid());
		final String absolute_url = String.format(url, cityname, appid, units);
		LOGGER.info("Accessing the URL for city {} using units as {}", cityname, units);
		final ResponseEntity<WeatherData> response;
		try {
			response = restTemplate.getForEntity(absolute_url, WeatherData.class);
			final HttpStatus responseStatus = response.getStatusCode();
			LOGGER.info("Response code : {}", response.getStatusCode());
			if (responseStatus == HttpStatus.OK) {
				return response.getBody();
			} else {
				throw new WeatherServerAccessException(INTERNAL_SERVICE_ERROR_MSG);
			}
		} catch (final HttpClientErrorException clientException) {
			LOGGER.error("Error : " + clientException.getMessage());
			throw new InvalidCityNameException("City " + cityname + " does not exists.");
		}
	}

	private String getBase64DecodedAppId(final String appid) {
		byte[] decodedBytes = Base64.getDecoder().decode(appid);
		return new String(decodedBytes);

	}

}
