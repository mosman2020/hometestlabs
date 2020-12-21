package com.hometest.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {

	@Autowired
	private Environment env;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin(env.getRequiredProperty("cors.allowedOrigin"));
		config.addAllowedHeader(env.getRequiredProperty("cors.allowedHeader"));

		for (String method : env.getRequiredProperty("cors.allowedMethods", String[].class))
			config.addAllowedMethod(method);

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
