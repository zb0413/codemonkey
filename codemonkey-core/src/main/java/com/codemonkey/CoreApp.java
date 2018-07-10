package com.codemonkey;

import java.util.Locale;

import javax.servlet.Filter;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class CoreApp extends SpringBootServletInitializer{
	
	@Bean SysProp getSysProp(){
		return new SysProp();
	}
	
	@Bean
	FilterRegistrationBean setMechFilter(){
		Filter filter = new ConfigurableSiteMeshFilter();
		return new FilterRegistrationBean(filter);
	}
	
	@Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return slr;
    }
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(CoreApp.class, args);
	}
}
