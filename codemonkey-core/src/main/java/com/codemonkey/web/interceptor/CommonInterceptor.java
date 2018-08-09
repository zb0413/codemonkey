package com.codemonkey.web.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class CommonInterceptor implements HandlerInterceptor {

	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		watchingRequestParameters(request);
		
		return true;
	}


	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
	private void watchingRequestParameters(HttpServletRequest request) {
		
		String uri = request.getRequestURI();
		if(!uri.endsWith(".js") && !uri.endsWith(".css") && !uri.endsWith(".png") && !uri.endsWith(".jpg") && !uri.endsWith(".gif")){
			log.info(">>>>>>>>>>>>>>>>>>>>>>>> request >>>>>>>>>>>>>>>>>>>>>>>>");
			log.info(uri);
			Enumeration<?> enumeration = request.getParameterNames();
			while(enumeration.hasMoreElements()) {
				String name = (String) enumeration.nextElement();
				log.info(name + "  =:=  " + request.getParameter(name));
			}
			log.info("<<<<<<<<<<<<<<<<<<<<<<<< request <<<<<<<<<<<<<<<<<<<<<<<<");
		}
		
	}

}
