package com.codemonkey;

import java.util.List;

import javax.servlet.Filter;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppUserService;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.interceptor.CommonInterceptor;

public class ActivitiApplication extends CoreApp {
	
	@Autowired private AppUserService appUserService;
	
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
	
	@Bean
    InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {

        return new InitializingBean() {
            public void afterPropertiesSet() throws Exception {
            	
            	List<AppUser> list = appUserService.findAll();
            	
            	if(SysUtils.isNotEmpty(list)){
            		for(AppUser appUser : list){
            			User user = identityService.createUserQuery().userId(appUser.getUsername()).singleResult();
                    	if(user == null){
                    		user = identityService.newUser(appUser.getUsername());
                    		user.setPassword(appUser.getPassword());
                            identityService.saveUser(user);
                    	}
            		}
            	}else{
            		User user = identityService.createUserQuery().userId("anonymous").singleResult();
                	if(user == null){
                		user = identityService.newUser("anonymous");
                		user.setPassword("anonymous");
                        identityService.saveUser(user);
                	}
            	}
            }
        };
    }
	
	public static void main(String[] args) throws Exception {
		
		Object[] configures = new Object[]{
				"classpath*:/spring/applicationContext-*.xml",
				ActivitiApplication.class
		};
		SpringApplication.run(configures, args);
	}
	
}
