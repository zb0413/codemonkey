package com.codemonkey.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.codemonkey.error.SysError;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class ExceptionHandlerController {
	
	@ExceptionHandler(SysError.class)
	public String handlerSysError(Exception ex, HttpServletRequest request , HttpServletResponse response) {
	    log.error(ex.getMessage(), ex);
	    request.setAttribute("errorTips", ex.getMessage());
	    request.setAttribute("ex", ex);
	    return "error/sysError";
	}
	
	@ExceptionHandler(Exception.class)
	public String handlerAll(Exception ex, HttpServletRequest request , HttpServletResponse response) {
	    log.error(ex.getMessage(), ex);
	    request.setAttribute("errorTips", ex.getMessage());
	    request.setAttribute("ex", ex);
	    return "error/500";
	}   
	/**
	 * 处理 NoHandlerFoundException 类型的异常
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handlerNoFound(NoHandlerFoundException ex, HttpServletRequest request , HttpServletResponse response) {
	    log.error(ex.getMessage(), ex);
	    request.setAttribute("errorTips", ex.getMessage());
	    return "error/404";
	}
}
