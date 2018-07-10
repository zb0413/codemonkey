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
@RequestMapping("/ext/taskList/**")
public class TaskListController extends AbsController{

	@Autowired private WorkflowService workflowService;
	
	@RequestMapping("completeTask")
	@ResponseBody
	public String startProcessInstance(
			@RequestParam String id,
			@RequestParam(required = false) String toTransitionName){
		
		JSONObject json = new JSONObject();
		json.put(ExtConstant.SUCCESS, true);
		
		workflowService.complete(id, toTransitionName);
		
		return json.toString();
	}
}
