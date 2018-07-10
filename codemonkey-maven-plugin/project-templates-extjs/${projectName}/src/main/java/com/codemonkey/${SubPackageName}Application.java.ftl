package com.codemonkey;

import javax.servlet.Filter;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.interceptor.CommonInterceptor;


@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
public class ${SubPackageName}Application extends SpringBootServletInitializer {
	
	@Bean
	FilterRegistrationBean setMechFilter(){
		Filter filter = new ConfigurableSiteMeshFilter();
		return new FilterRegistrationBean(filter);
	}
	
	@Bean
	CommonInterceptor commonInterceptor(){
		return new CommonInterceptor();
	}
	
	@Bean
	FilterRegistrationBean setEncodingFilter(){
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding(SysUtils.UTF8);
		return new FilterRegistrationBean(filter);
	}
	
	public static void main(String[] args) throws Exception {
		
		Object[] configures = new Object[]{
				"classpath*:/spring/applicationContext-*.xml",
				${SubPackageName}Application.class
		};
		SpringApplication.run(configures, args);
	}
	
}
