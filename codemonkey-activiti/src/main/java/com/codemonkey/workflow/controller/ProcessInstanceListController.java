package com.codemonkey.workflow.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.utils.ExtConstant;
import com.codemonkey.web.controller.AbsController;
import com.codemonkey.workflow.WorkflowService;

@Controller
@RequestMapping("/ext/processInstanceList/**")
public class ProcessInstanceListController extends AbsController{

	@Autowired private WorkflowService workflowService;
	
	@RequestMapping("suspendProcessInstance")
	@ResponseBody
	public String suspendProcessInstance(@RequestParam String businessKey){
		
		JSONObject json = new JSONObject();
		json.put(ExtConstant.SUCCESS, true);
		
		workflowService.suspendProcessInstance(businessKey);
		
		return json.toString();
	}
	
	@RequestMapping("activateProcessInstance")
	@ResponseBody
	public String activateProcessInstance(@RequestParam String businessKey){
		
		JSONObject json = new JSONObject();
		json.put(ExtConstant.SUCCESS, true);
		
		workflowService.activateProcessInstance(businessKey);
		
		return json.toString();
	}
	
	@RequestMapping("deleteProcessInstance")
	@ResponseBody
	public String deleteProcessInstance(
			@RequestParam String businessKey,
			@RequestParam String deleteReason){
		
		JSONObject json = new JSONObject();
		json.put(ExtConstant.SUCCESS, true);
		
		workflowService.deleteProcessInstance(businessKey, deleteReason);
		
		return json.toString();
	}
	
}
