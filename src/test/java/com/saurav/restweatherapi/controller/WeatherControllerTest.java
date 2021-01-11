package com.saurav.restweatherapi.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.saurav.restweatherapi.domain.Main;
import com.saurav.restweatherapi.domain.Weather;
import com.saurav.restweatherapi.domain.WeatherData;
import com.saurav.restweatherapi.exception.InvalidCityNameException;
import com.saurav.restweatherapi.exception.WeatherAppExceptionHandler;
import com.saurav.restweatherapi.exception.WeatherServerAccessException;
import com.saurav.restweatherapi.service.WeatherProviderApiConsumer;


@AutoConfigureMockMvc
@ContextConfiguration(classes = {WeatherController.class, WeatherAppExceptionHandler.class})
@WebMvcTest
public class WeatherControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private RestTemplate mockRestTemplate;

	@MockBean
	@Qualifier("OpenweathermapProviderApiConsumer")
	WeatherProviderApiConsumer mockApiConsumer;
	
	@Test
	@DisplayName("Test case to get the data for a valid city with valid request parameter")
	public void testRetrieveWeatherForACity() throws Exception {
		final String expectedWeatherData = "{\"visibility\":3000.0,\"name\":\"Kolkata\",\"weather\":[{\"main\":\"Haze\",\"description\":\"haze\"}],\"main\":{\"temp\":20.0,\"humidity\":77.0,\"feels_like\":20.46,\"temp_min\":20.0,\"temp_max\":20.0}}";
		final WeatherData weatherDataObject = createWeatherData();
		when(mockApiConsumer.getWeatherData(eq("kolkata"), eq("metric")))
				.thenReturn(weatherDataObject);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myapp/weather/kolkata?units=metric")
				.accept(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(expectedWeatherData, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	@DisplayName("Test case to get the data for a valid city without request parameter")
	public void testRetrieveWeatherForACityWithoutRequestParameter() throws Exception {
		final String expectedWeatherData = "{\"visibility\":3000.0,\"name\":\"Kolkata\",\"weather\":[{\"main\":\"Haze\",\"description\":\"haze\"}],\"main\":{\"temp\":20.0,\"humidity\":77.0,\"feels_like\":20.46,\"temp_min\":20.0,\"temp_max\":20.0}}";
		final WeatherData weatherDataObject = createWeatherData();
		when(mockApiConsumer.getWeatherData(eq("kolkata"), eq("metric")))
				.thenReturn(weatherDataObject);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myapp/weather/kolkata")
				.accept(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(expectedWeatherData, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	@DisplayName("Test case to get the data for a invalid city name")
	public void testRetrieveWeatherForAInvalidCity() throws Exception {
		String expectedErrorMessage = "{\"message\":\"City Cricket does not exists.\"}";
		when(mockApiConsumer.getWeatherData(eq("Cricket"), eq("metric")))
				.thenThrow(new InvalidCityNameException("City Cricket does not exists."));
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myapp/weather/Cricket")
				.accept(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(expectedErrorMessage, result.getResponse().getContentAsString(), false);
	}

	@Test
	@DisplayName("Test case to get the data for a valid city with valid request parameter")
	public void testInvalidRequestParameters() throws Exception {
		final String expectedErrorMessage = "{\"message\":\"Invalid value for units field. Only supported units are metric and imperial\"}";
		final WeatherData weatherDataObject = createWeatherData();
		when(mockApiConsumer.getWeatherData(eq("kolkata"), eq("metric")))
				.thenReturn(weatherDataObject);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myapp/weather/kolkata?units=wrongvalue")
				.accept(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(expectedErrorMessage, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	@DisplayName("Test case to verify WeatherServerAceessException is thrown if invalid city name is provided ")
	public void testServerErrorResponse() throws Exception {
		String expectedErrorMessage = "{\"message\":\"Internal service error while retrieving data from the whether provider service.\"}";
		when(mockApiConsumer.getWeatherData(eq("kolkat"), eq("metric")))
				.thenThrow(new WeatherServerAccessException("Internal service error while retrieving data from the whether provider service."));
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myapp/weather/kolkat?units=metric")
				.accept(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(expectedErrorMessage, result.getResponse().getContentAsString(), false);
	}
	
	private WeatherData createWeatherData() {
		Main mainObj = new Main();
		mainObj.setFeelsLike(20.46);
		mainObj.setHumidity(77.0);
		mainObj.setTemp(20.0);
		mainObj.setTempMax(20.0);
		mainObj.setTempMin(20.0);
		Weather weatherObj = new Weather();
		weatherObj.setWeatherDescription("haze");
		weatherObj.setWeatherType("Haze");
		WeatherData weatherDataObj = new WeatherData();
		weatherDataObj.setName("Kolkata");
		weatherDataObj.setVisibility(3000.0);
		weatherDataObj.setMain(mainObj);
		List<Weather> weatherObjList = new ArrayList<>();
		weatherObjList.add(weatherObj);
		weatherDataObj.setWeather(weatherObjList);
		return weatherDataObj;

	}

}
