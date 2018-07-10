package com.codemonkey.web.interceptor;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.controller.AbsAuthController;

@Component
@Lazy(false)
@Transactional(propagation=Propagation.REQUIRED)
public class AuthFilterServiceImpl extends AuthenticationFilter {

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
        	updateLastVisit(request);
            return true;
        } else {
        	HttpServletRequest r = (HttpServletRequest) request;
        	if(SysUtils.isAjax(r)){
        		
//        		throw new SessionTimeoutError();
        		JSONObject jo = new JSONObject();
        		jo.put(ExtConstant.SUCCESS, false);
        		jo.put(ExtConstant.ERROR_CODE, ExtConstant.SESSION_TIME_OUT_ERROR_CODE);
        		jo.put(ExtConstant.ERROR_KEY, "sessionTimeout");
        		jo.put(ExtConstant.ERROR_MSG , "会话超时 , 请重新登录");
        		response.getWriter().write(jo.toString());
        	}else{
        		updateLastVisit(request);
        		saveRequestAndRedirectToLogin(request, response);
        	}
    		return false;
        }
    }

	private void updateLastVisit(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		session.setAttribute(AbsAuthController.LAST_VISIT_URL, req.getRequestURI());
		session.setAttribute(AbsAuthController.LAST_VISIT_PARAMS, req.getParameterMap());
	}

}
