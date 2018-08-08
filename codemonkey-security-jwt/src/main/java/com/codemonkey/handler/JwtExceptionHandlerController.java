package com.codemonkey.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.codemonkey.error.SysError;
import com.codemonkey.utils.ResponseUtil;

@Slf4j
@RestControllerAdvice
public class JwtExceptionHandlerController {

	@ExceptionHandler(AuthenticationServiceException.class)
	public void authenticationServiceException(AuthenticationServiceException ex, HttpServletRequest request , HttpServletResponse response) {
	    log.error(ex.getMessage(), ex);
	    ResponseUtil.out(response, ResponseUtil.resultMap(false , 500 , ex.getMessage()));
	}
	
	@ExceptionHandler(SysError.class)
	public void handlerSysError(SysError ex, HttpServletRequest request , HttpServletResponse response) {
	    log.error(ex.getMessage(), ex);
	    ResponseUtil.out(response, ResponseUtil.resultMap(false , 500 , ex.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
	public void handlerAll(Exception ex, HttpServletRequest request , HttpServletResponse response) {
	    log.error(ex.getMessage(), ex);
	    ResponseUtil.out(response, ResponseUtil.resultMap(false , 500 , ex.getMessage()));
	}   
	/**
	 * 处理 NoHandlerFoundException 类型的异常
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public void handlerNoFound(NoHandlerFoundException ex, HttpServletRequest request , HttpServletResponse response) {
	    log.error(ex.getMessage(), ex);
	    ResponseUtil.out(response, ResponseUtil.resultMap(false , 404 , ex.getMessage()));
	}

}
