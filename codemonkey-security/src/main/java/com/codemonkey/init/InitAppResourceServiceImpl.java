package com.codemonkey.init;

import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.codemonkey.domain.AppResource;
import com.codemonkey.service.AppResourceService;
import com.codemonkey.utils.SysUtils;

import io.swagger.annotations.ApiOperation;

@Component
@InitOrder(6)
@Transactional
public class InitAppResourceServiceImpl implements InitDataBean{

	@Autowired private AbstractHandlerMethodMapping<RequestMappingInfo> objHandlerMethodMapping;
	
	@Autowired private AppResourceService appResourceService;
	
	@Override
	public void doInit() {
		
		Map<RequestMappingInfo, HandlerMethod> mapRet = objHandlerMethodMapping.getHandlerMethods();
		
		Set<RequestMappingInfo> set = mapRet.keySet();
		
		for(RequestMappingInfo r : set ) {
			HandlerMethod method = mapRet.get(r);
			ApiOperation op = method.getMethodAnnotation(ApiOperation.class);
			createAppResources(op , r.getPatternsCondition().getPatterns() , r.getMethodsCondition().getMethods());
		}
		
	}

	private void createAppResources(ApiOperation op, Set<String> patterns, Set<RequestMethod> methods) {
		String desc = "";
		if(op != null) {
			if(SysUtils.isNotEmpty(op.value())) {
				desc += op.value() + ',';
			}
			if(SysUtils.isNotEmpty(op.notes())) {
				desc += op.notes();
			}
		}
		
		for(String p : patterns) {
			for(RequestMethod m : methods) {
				
				AppResource ar = appResourceService.findBy("urlPattern&&methodName", p , m.name());
				
				if(ar == null) {
					ar = new AppResource();
					ar.setUrlPattern(p);
					ar.setMethodName(m.name());
						
				}
				ar.setDescription(desc);
				appResourceService.save(ar);
			}
		}
	}
}
