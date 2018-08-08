package com.codemonkey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.codemonkey.handler.JWTAuthenticationFilter;
import com.codemonkey.handler.JwtAuthenticationFailHandler;
import com.codemonkey.handler.JwtAuthenticationSuccessHandler;
//@EnableGlobalMethodSecurity(prePostEnabled=true)

@Configuration
public class JwtSecurityConfig extends WebSecurityAdapter {

    @Autowired
    private JwtAuthenticationSuccessHandler successHandler;

    @Autowired
    private JwtAuthenticationFailHandler failHandler;
    
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
	     
	    //前后端分离采用JWT 不需要session
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        //添加JWT过滤器 除/login其它请求都需经过此过滤器
        .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }
	
	@Override
	protected void login(HttpSecurity http) throws Exception {
		http.formLogin().loginPage(getSecurityConfig().getLoginView()).loginProcessingUrl(getSecurityConfig().getLoginUrl()).permitAll()
        //成功处理类
        .successHandler(successHandler)
        //失败
        .failureHandler(failHandler)
        .and();
	}
}
