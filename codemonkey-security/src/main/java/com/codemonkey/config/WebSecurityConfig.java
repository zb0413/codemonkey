package com.codemonkey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.codemonkey.handler.AuthenticationFailHandler;
import com.codemonkey.handler.AuthenticationSuccessHandler;
import com.codemonkey.handler.RestAccessDeniedHandler;
import com.codemonkey.permission.MyFilterSecurityInterceptor;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailHandler failHandler;

    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(securityConfig.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
    				.and()
    			.authorizeRequests().antMatchers((String[]) securityConfig.getAnonymousUrls().toArray()).permitAll()
    				.and()
	            //表单登录方式
	            .formLogin().loginPage(securityConfig.getLoginView()).loginProcessingUrl(securityConfig.getLoginUrl()).permitAll()
	            //成功处理类
	            .successHandler(successHandler)
	            //失败
	            .failureHandler(failHandler)
	            	.and()
                .logout()
                .logoutUrl(securityConfig.getLogoutUrl())
                .permitAll()
                	.and()
                .authorizeRequests().anyRequest().authenticated()
                	.and()
                //关闭跨站请求防护
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
   
}
