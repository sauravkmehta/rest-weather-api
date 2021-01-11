package com.saurav.restweatherapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.saurav.restweatherapi.domain.WeatherData;
import com.saurav.restweatherapi.exception.InvalidCityNameException;
import com.saurav.restweatherapi.exception.WeatherServerAccessException;

@ExtendWith(MockitoExtension.class)
class OpenweathermapProviderApiConsumerTest {

	@Mock
	private RestTemplate mockRestTemplate;

	@Mock
	private ConfigurationReader mockConfigReader;

	@Mock
	private ResponseEntity<WeatherData> mockResponseEntity;

	@InjectMocks
	private OpenweathermapProviderApiConsumer testObj;

	private static final String INTERNAL_SERVICE_ERROR_MSG = "Internal service error while retrieving data from the whether provider service.";
	private final String url = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=%s";

	@Test
	@DisplayName("Test case to retrieve weather data from weather provider")
	void testGetWeatherData_ForValidResponse() {
		// Given
		when(mockConfigReader.getAppid()).thenReturn("bXlhcHBpZA==");
		when(mockConfigReader.getUrl()).thenReturn(url);
		when(mockRestTemplate.getForEntity(anyString(), eq(WeatherData.class))).thenReturn(mockResponseEntity);
		when(mockResponseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		when(mockResponseEntity.getBody()).thenReturn(new WeatherData());
		// When
		WeatherData weatherData = testObj.getWeatherData("dublin", "metric");
		// Then
		verify(mockRestTemplate, times(1)).getForEntity(anyString(), eq(WeatherData.class));
		assertNotNull(weatherData);
	}

	@Test
	@DisplayName("Test case to retrieve weather data from weather provider if response code is not 200. This should"
			+ "throw an WeatherServerAccessException exception.")
	void testGetWeatherData_ForInvalidResponseCode() {
		// Given
		when(mockConfigReader.getAppid()).thenReturn("bXlhcHBpZA==");
		when(mockConfigReader.getUrl()).thenReturn(url);
		when(mockRestTemplate.getForEntity(anyString(), eq(WeatherData.class))).thenReturn(mockResponseEntity);
		when(mockResponseEntity.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
		// When
		Exception exception = assertThrows(WeatherServerAccessException.class, () -> {
			testObj.getWeatherData("dublin", "metric");
		});
		// Then
		verify(mockRestTemplate, times(1)).getForEntity(anyString(), eq(WeatherData.class));
		assertEquals(INTERNAL_SERVICE_ERROR_MSG, exception.getMessage());
	}

	@Test
	@DisplayName("Test case to retrieve weather data from weather provider if city name is invalid. This should"
			+ "throw an InvalidCityNameException exception.")
	void testGetWeatherData_ForInvalidCity() {
		// Given
		when(mockConfigReader.getAppid()).thenReturn("bXlhcHBpZA==");
		when(mockConfigReader.getUrl()).thenReturn(url);
		when(mockRestTemplate.getForEntity(anyString(), eq(WeatherData.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		// When
		Exception exception = assertThrows(InvalidCityNameException.class, () -> {
			testObj.getWeatherData("dublinaa", "metric");
		});
		// Then
		verify(mockRestTemplate, times(1)).getForEntity(anyString(), eq(WeatherData.class));
		assertEquals("City dublinaa does not exists.", exception.getMessage());
	}
}
