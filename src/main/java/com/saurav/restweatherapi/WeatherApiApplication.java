package com.saurav.restweatherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * {@link WeatherApiApplication} is main
 * {@link org.springframework.boot.autoconfigure.SpringBootApplication}. This
 * uses {@link org.springframework.context.annotation.ComponentScan} to identify
 * all the services and controller and
 * {@link org.springframework.boot.autoconfigure.domain.EntityScan} to identify
 * the domain objects.
 * 
 * @author Saurav
 *
 */
@SpringBootApplication
@ComponentScan({ "com.saurav.restweatherapi.service", "com.saurav.restweatherapi.controller",
		"com.saurav.restweatherapi.exception" })
@EntityScan("com.saurav.restweatherapi.domain")
public class WeatherApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(WeatherApiApplication.class, args);
	}

}
