package com.codemonkey.web.interceptor;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.codemonkey.SysProp;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;

@Component
public class CommonInterceptor implements HandlerInterceptor {

	private Logger log = SysUtils.getLog(CommonInterceptor.class);
	
	@Autowired private SysProp sysProp;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		watchingRequestParameters(request);
		
		setTheme(request);
		
		setLocale(request);
		
		setModule(request);
		
		SysUtils.syncToSysUtils(request);
		
		return true;
	}

	private void setModule(HttpServletRequest request) {
		String param = request.getParameter(ExtConstant.CURRENT_MODULE);
		HttpSession session = request.getSession();
		if(StringUtils.isNotBlank(param)){
			session.setAttribute(ExtConstant.CURRENT_MODULE, param);
		}
	}

	private void setLocale(HttpServletRequest request) {
		
		Map<String , String[]> map = request.getParameterMap();
		HttpSession session = request.getSession();
		
		if(map.containsKey(ExtConstant.LOCALE)){
			String param = request.getParameter(ExtConstant.LOCALE);
			if(StringUtils.isNotBlank(param)){
				session.setAttribute(ExtConstant.LOCALE, param);
			}
		}else{
			if(session.getAttribute(ExtConstant.LOCALE) == null){
				session.setAttribute(ExtConstant.LOCALE, ExtConstant.DEFAULT_LOCALE);
			}
		}
	}

	private void setTheme(HttpServletRequest request) {
		String theme = request.getParameter(ExtConstant.THEME);
		if(StringUtils.isBlank(theme)){
			theme = ExtConstant.DEFAULT_THEME;
		}
		request.getSession().setAttribute(ExtConstant.THEME, theme);
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
		
		if(sysProp.isLogSession()){
			log.info(">>>>>>>>>>>>>>>>>>>>>>>> session >>>>>>>>>>>>>>>>>>>>>>>>");
			HttpSession session = request.getSession();
			Enumeration<?> enumeration = session.getAttributeNames();
			while(enumeration.hasMoreElements()) {
				String name = (String)enumeration.nextElement();
				log.info(name  + "  =:=  " + session.getAttribute(name));
			}
			log.info("<<<<<<<<<<<<<<<<<<<<<<<< session <<<<<<<<<<<<<<<<<<<<<<<<");
		}
		
		if(sysProp.isLogRequest()){
			
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

}
