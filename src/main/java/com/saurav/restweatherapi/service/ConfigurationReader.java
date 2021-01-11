package com.saurav.restweatherapi.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@link ConfigurationReader} will read the application configuration from
 * application.yml file. This is reading URL and appid to access weather
 * provider site.
 * 
 * @author Saurav
 *
 */

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ConfigurationReader {

	private String url;
	private String appid;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

}
