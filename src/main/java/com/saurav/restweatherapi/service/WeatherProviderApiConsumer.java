package com.saurav.restweatherapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.saurav.restweatherapi.domain.WeatherData;

/**
 * Abstract class {@link WeatherProviderApiConsumer} which consume weather provider's API.
 * 
 * @author Saurav
 *
 */
public abstract class WeatherProviderApiConsumer {
	@Autowired
	RestTemplate restTemplate;

	public abstract WeatherData getWeatherData(final String cityname, final String units);
	
	@Bean
	public RestTemplate restTemplate() {
		final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(3000);
		requestFactory.setReadTimeout(3000);
		return new RestTemplate(requestFactory);
	}

}
