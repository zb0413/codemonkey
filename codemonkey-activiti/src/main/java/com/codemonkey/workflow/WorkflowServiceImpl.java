package com.codemonkey.workflow;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowServiceImpl implements WorkflowService{
	
	private static final String BUSINESS_KEY = "businessKey";

	private static final String TRANSITION_NAME = "transitionName";

	private static final String SYSTEM_AUTO = "SYSTEM_ASSIGNEE";
	
	@Autowired private ProcessEngine processEngine;
	
	@Autowired protected RepositoryService repositoryService;
	
	@Autowired protected IdentityService identityService;
	
	public Deployment deployBPMN20FileByClassPath(String path) {
		Deployment deploy = getRepositoryService().createDeployment().addClasspathResource(path).deploy();
		return deploy;
	}
	
	@Override
	public Deployment deployBPMN20File(String resourceName , InputStream inputStream) {
		Deployment deploy = getRepositoryService().createDeployment().addInputStream(resourceName, inputStream).deploy();
		return deploy;
	}

	@Override
	public ProcessInstance startProcessInstance(String processDefinitionKey){
		return startProcessInstance(processDefinitionKey , null);
	}
	
	@Override
	public ProcessInstance startProcessInstance(String processDefinitionKey , Map<String, Object> variables) {
		
		String businessKey = generateBusinessKey(processDefinitionKey);

		ProcessInstance processInstance = null;
		
		if(variables != null){
			variables.put(BUSINESS_KEY,businessKey);
			processInstance = getRuntimeService().startProcessInstanceByKey(processDefinitionKey , businessKey, variables);
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(BUSINESS_KEY,businessKey);
			processInstance = getRuntimeService().startProcessInstanceByKey(processDefinitionKey , businessKey, map);
		}
		return processInstance;	
	}
	
	private String generateBusinessKey(String processDefinitionKey) {
		long count = getRuntimeService().createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).count();
		return processDefinitionKey + '-' +(count+1);
	}

	private ProcessInstance getInstance(String businessKey) {
		ProcessInstance instance = null;
		if(StringUtils.isNotBlank(businessKey)) {
			instance = getRuntimeService().createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
		}
		return instance;
	}
	
	@Override
	public List<Task> getActiveTasks(String businessKey) {
		return getActiveTasksByUserId(businessKey , null);
	}
	
	public List<Task> getActiveTasksByUserId(String businessKey, String userId){
		ProcessInstance instance = getInstance(businessKey);
		List<Task> list = new ArrayList<Task>();
		if(instance != null){
			list = getActiveTasksByUser(instance, list, userId);
		}
		return list;
	}
	
	private List<Task> getActiveTasksByUser(ProcessInstance instance, List<Task> list, String userId){
		if(list == null) {
			return new ArrayList<Task>();
		}
		
		if(StringUtils.isNotBlank(userId)){
			List<Task> taskList = getTaskService().createTaskQuery().processInstanceId(instance.getId()).taskCandidateUser(userId).list();
			for (Task task : taskList) {
				list.add(task);
			}
			ProcessInstance subInstance = getRuntimeService().createProcessInstanceQuery().subProcessInstanceId(instance.getId()).singleResult();
			if(subInstance != null){
				list = getActiveTasksByUser(subInstance, list, userId);
			}
		}else{
			List<Task> taskList = getTaskService().createTaskQuery().processInstanceId(instance.getId()).list();
			for (Task task : taskList) {
				list.add(task);
			}
			ProcessInstance subInstance = getRuntimeService().createProcessInstanceQuery().subProcessInstanceId(instance.getId()).singleResult();
			if(subInstance != null){
				list = getActiveTasksByUser(subInstance, list, userId);
			}
		}
		return list;
	}
	
	@Override
	public List<Task> getSystemActiveTasks(String businessKey) {
		return getActiveTasksByUserId(businessKey , SYSTEM_AUTO);
	}
	
	public void complete(String taskId, String toTransitionName){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put(TRANSITION_NAME, toTransitionName);
		getTaskService().complete(taskId, variables);
	}
	
	private RuntimeService getRuntimeService() {
		return processEngine.getRuntimeService();
	}	
	
	private RepositoryService getRepositoryService() {
		return processEngine.getRepositoryService();
	}

	private TaskService getTaskService() {
		return processEngine.getTaskService();
	}
	
	@Override
	public void suspendProcessInstance(String businessKey) {
		
		ProcessInstance instance = getInstance(businessKey);
		
		if(instance != null){
			if(!instance.isSuspended() && !instance.isEnded()){
				getRuntimeService().suspendProcessInstanceById(instance.getId());
			}
		}
	}

	@Override
	public void activateProcessInstance(String businessKey) {
		ProcessInstance instance = getInstance(businessKey);
		
		if(instance != null){
			if(instance.isSuspended()){
				getRuntimeService().activateProcessInstanceById(instance.getId());
			}
		}
	}
	
	@Override
	public void deleteProcessInstance(String businessKey , String deleteReason) {
		ProcessInstance instance = getInstance(businessKey);
		if(instance != null){
			getRuntimeService().deleteProcessInstance(instance.getId(), deleteReason);
		}
	}
}


