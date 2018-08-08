package com.codemonkey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.codemonkey.handler.AuthenticationFailHandler;
import com.codemonkey.handler.AuthenticationSuccessHandler;
import com.codemonkey.handler.RestAccessDeniedHandler;
import com.codemonkey.permission.MyFilterSecurityInterceptor;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityAdapter {

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailHandler failHandler;

    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    
    @Override
    protected void login(HttpSecurity http) throws Exception {
		http.formLogin().loginPage(getSecurityConfig().getLoginView()).loginProcessingUrl(getSecurityConfig().getLoginUrl()).permitAll()
          //成功处理类
          .successHandler(successHandler)
          //失败
          .failureHandler(failHandler)
          .and();
	}
    
    @Override
    protected void exceptionHandling(HttpSecurity http)  throws Exception {
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}
    
}
