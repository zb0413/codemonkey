package com.codemonkey.workflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.web.controller.AbsController;

@Controller
@RequestMapping("/ext/processDesigner/**")
public class ProcessDesignerController extends AbsController{
	
	@RequestMapping("show")
	public String show(){
		return "processDesigner";
	}
}
