package com.codemonkey.workflow.controller;

import java.io.InputStream;

import javax.servlet.http.HttpSession;

import org.activiti.engine.repository.Deployment;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.codemonkey.domain.UploadEntry;
import com.codemonkey.service.UploadEntryService;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.web.controller.AbsController;
import com.codemonkey.workflow.WorkflowService;

@Controller
@RequestMapping("/ext/processList/**")
public class ProcessListController extends AbsController{

	@Autowired private WorkflowService workflowService;
	@Autowired private UploadEntryService uploadEntryService;
	
	@RequestMapping("uploadProcess")
	@ResponseBody
	public String uploadProcess(HttpSession session, 
			@RequestParam(required = false) String name,
			@RequestParam("file") MultipartFile file){
		
		JSONObject json = new JSONObject();
		json.put(ExtConstant.SUCCESS, true);
		
		// 1.保存文件
		UploadEntry uploadEntry = uploadEntryService.doSaveFile(session, file);
		
		//2.部署流程
		try{
			System.out.print(new String(file.getBytes()));
			if(file != null){
				InputStream input = file.getInputStream();
				if(input != null){
					Deployment deployment = workflowService.deployBPMN20File(uploadEntry.getFilePath() , input);
					json.put("message", deployment.getId());
					json.put("deploymentId", deployment.getId());
					json.put("name", deployment.getName());
					json.put("deploymentTime", deployment.getDeploymentTime());
					json.put("tenantId", deployment.getTenantId());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	@RequestMapping("startProcessInstance")
	@ResponseBody
	public String startProcessInstance(@RequestParam String key){
		
		JSONObject json = new JSONObject();
		json.put(ExtConstant.SUCCESS, true);
		
		workflowService.startProcessInstance(key);
		
		return json.toString();
	}
	
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
	
}
