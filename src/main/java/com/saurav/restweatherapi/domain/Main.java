package com.saurav.restweatherapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {
	@JsonProperty("temp")
	private double temp;
	@JsonProperty("feels_like")
	private double feelsLike;
	@JsonProperty("temp_min")
	private double tempMin;
	@JsonProperty("temp_max")
	private double tempMax;
	@JsonProperty("humidity")
	private double humidity;

	public void setTemp(final double temp) {
		this.temp = temp;
	}

	public void setFeelsLike(final double feelsLike) {
		this.feelsLike = feelsLike;
	}

	public void setTempMin(final double d) {
		this.tempMin = d;
	}

	public void setTempMax(final double tempMax) {
		this.tempMax = tempMax;
	}

	public void setHumidity(final double humidity) {
		this.humidity = humidity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(feelsLike);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(humidity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.temp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tempMax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tempMin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Main other = (Main) obj;
		if (Double.doubleToLongBits(feelsLike) != Double.doubleToLongBits(other.feelsLike))
			return false;
		if (Double.doubleToLongBits(humidity) != Double.doubleToLongBits(other.humidity))
			return false;
		if (Double.doubleToLongBits(temp) != Double.doubleToLongBits(other.temp))
			return false;
		if (Double.doubleToLongBits(tempMax) != Double.doubleToLongBits(other.tempMax))
			return false;
		if (Double.doubleToLongBits(tempMin) != Double.doubleToLongBits(other.tempMin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Main [temp=" + temp + ", feelsLike=" + feelsLike + ", tempMin=" + tempMin + ", tempMax=" + tempMax
				+ ", humidity=" + humidity + "]";
	}

}
