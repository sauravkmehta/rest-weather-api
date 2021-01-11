package com.saurav.restweatherapi.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This represents the main weather JSON object which has two child
 * {@link Weather} and {@link Main}.
 * 
 * @author Saurav
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
	@JsonProperty("weather")
	private List<Weather> weather;
	@JsonProperty("main")
	private Main main;
	@JsonProperty("visibility")
	private double visibility;
	@JsonProperty("name")
	private String name;

	public void setMain(final Main main) {
		this.main = main;
	}

	public void setVisibility(final double d) {
		this.visibility = d;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setWeather(final List<Weather> weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "WeatherData [weather=" + weather + ", main=" + main + ", visibility=" + visibility + ", name=" + name
				+ "]";
	}

}
