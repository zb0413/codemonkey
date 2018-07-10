package com.codemonkey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.LogRequest;
import com.codemonkey.service.log.LogRequestService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/logRequestList/**")
public class LogRequestListController extends AbsListExtController<LogRequest>{

	@Autowired private LogRequestService logRequestService;

	@Override
	protected LogRequestService service() {
		return logRequestService;
	}
	
	
	
}
