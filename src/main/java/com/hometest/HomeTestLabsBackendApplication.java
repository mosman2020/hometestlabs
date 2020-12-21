package com.hometest;

import javax.validation.MessageInterpolator;
import javax.validation.Validator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.hometest.validation.CustomMessageInterpolator;

@SpringBootApplication
public class HomeTestLabsBackendApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(HomeTestLabsBackendApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HomeTestLabsBackendApplication.class);
	}
	

	 @Bean
	    public MessageInterpolator messageInterpolator() {
	        return new CustomMessageInterpolator(new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(messageSource())));
	    }

	 @Bean
	    public Validator validator() {
	        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
//	        factory.setValidationMessageSource(messageSource());
	        factory.setMessageInterpolator(messageInterpolator());
	        return factory;
	    }
		
	 @Bean
	    public MethodValidationPostProcessor initValidationPostProcessor(){
	        return new MethodValidationPostProcessor();
	    }
	 
		
	    @Bean
	    public ReloadableResourceBundleMessageSource messageSource() {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasename("classpath:locale/messages");
//	        messageSource.setBasename("i18n/messages");
//	        messageSource.setDefaultEncoding("UTF-8");
	        messageSource.setUseCodeAsDefaultMessage(false);
	        messageSource.setFallbackToSystemLocale(false);
	        messageSource.setCacheSeconds(3600); //refresh cache once per hour
	        return messageSource;
	    }  

}
