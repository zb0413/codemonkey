package com.codemonkey.web.interceptor;

import java.security.cert.Certificate;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.codemonkey.utils.CertificateUtils;
import com.codemonkey.utils.SysUtils;

@Component
public class CertificateInterceptor implements HandlerInterceptor{

	private static final String CERTIFICATE_EXPIRE = "/certificateExpire";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		
		if(uri.equals(CERTIFICATE_EXPIRE)){
			return true;
		}
		
		Resource r = SysUtils.getResource("classpath:*.cer");
		
		if(r == null){
			if(uri.equals(CERTIFICATE_EXPIRE)){
				return true;
			}else{
				response.sendRedirect(CERTIFICATE_EXPIRE);
				return false;
			}
		}
		
		Certificate certificate = CertificateUtils.getCertificate(r.getInputStream());
		
		boolean flag = CertificateUtils.verifyCertificate(new Date(), certificate);
		
		if(!flag){
			response.sendRedirect(CERTIFICATE_EXPIRE);
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
