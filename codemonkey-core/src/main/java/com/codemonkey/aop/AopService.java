package com.codemonkey.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class AopService {
	
	public void beforeSavingEntity(JoinPoint joinPoint) {
		
	}

}
