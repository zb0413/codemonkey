package com.codemonkey.LogReqInterceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.error.SysError;
import com.codemonkey.service.log.LogRequestService;

@Aspect
@Component
public class LogReqInterceptor {
	
	@Autowired
	private LogRequestService logRequestService;
	
	@Pointcut("execution(* com..*Service.*(..))")
    private void anyMethod(){}//定义一个切入点  
      
   
	@After("anyMethod() && @annotation(com.codemonkey.annotation.LogOp)")
    public void after(JoinPoint joinPoint){
    	logRequestService.saveLogRequest(joinPoint , null);
    }  
	
	@AfterThrowing(pointcut = "anyMethod()" , throwing = "throwable")  
    public void doAfterThrow(JoinPoint joinPoint , Throwable throwable){
		try{
			if(!(throwable instanceof SysError)){
				logRequestService.saveLogRequest(joinPoint , throwable);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }  
	
}
