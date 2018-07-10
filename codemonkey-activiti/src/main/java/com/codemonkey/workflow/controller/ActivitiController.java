package com.codemonkey.workflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.web.controller.AbsController;

@Controller
@RequestMapping("/activiti/**")
public class ActivitiController extends AbsController{

	@RequestMapping("/welcome")
	public String welcome(ModelMap model) {
		return "welcome";
	}
	
	@RequestMapping("/modeler")
	public String modeler(ModelMap model) {
		return "modeler";
	}
	
	@RequestMapping("/deploy")
	@ResponseBody
	public String deploy(ModelMap model) {
		return "deploy";
	}

}