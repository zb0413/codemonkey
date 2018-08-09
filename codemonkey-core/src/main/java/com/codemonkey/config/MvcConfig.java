package com.codemonkey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.codemonkey.web.interceptor.CommonInterceptor;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired private CommonInterceptor commonInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(commonInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
