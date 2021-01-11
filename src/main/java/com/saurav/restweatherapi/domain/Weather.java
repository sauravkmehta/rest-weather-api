package com.saurav.restweatherapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
	@JsonProperty("main")
	private String weatherType;
	@JsonProperty("description")
	private String weatherDescription;

	public void setWeatherType(final String weatherType) {
		this.weatherType = weatherType;
	}

	public void setWeatherDescription(final String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((weatherType == null) ? 0 : weatherType.hashCode());
		result = prime * result + ((weatherDescription == null) ? 0 : weatherDescription.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weather other = (Weather) obj;
		if (weatherType == null) {
			if (other.weatherType != null)
				return false;
		} else if (!weatherType.equals(other.weatherType))
			return false;
		if (weatherDescription == null) {
			if (other.weatherDescription != null)
				return false;
		} else if (!weatherDescription.equals(other.weatherDescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Weather [weatherType=" + weatherType + ", weather_description=" + weatherDescription + "]";
	}

}
