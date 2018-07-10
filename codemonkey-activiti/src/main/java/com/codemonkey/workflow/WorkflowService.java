package com.codemonkey.workflow;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public interface WorkflowService{

	void complete(String taskId, String toTransitionName);

	Deployment deployBPMN20FileByClassPath(String path);
	
	Deployment deployBPMN20File(String resourceName , InputStream inputStream);

	List<Task> getActiveTasks(String businessKey);

	ProcessInstance startProcessInstance(String processDefinitionKey , Map<String, Object> variables);
	
	ProcessInstance startProcessInstance(String processDefinitionKey);

	void suspendProcessInstance(String businessKey);

	void activateProcessInstance(String businessKey);
	
	void deleteProcessInstance(String businessKey , String deleteReason);

	List<Task> getActiveTasksByUserId(String businessKey, String userId);

	List<Task> getSystemActiveTasks(String businessKey);
	
//	//ProcessDefinition
//	List<ProcessDefinition> getPagedProcessDefinitionList(Integer start, Integer limit , String id ,String query, JSONArray sort, JSONObject queryInfo);
//	
//	long getProcessDefinitionCount(Integer start, Integer limit , String id ,String query, JSONArray sort, JSONObject queryInfo);
//	
//	List<ProcessDefinition> getAllProcessDefinitionList(String id , String query, JSONArray sort, JSONObject queryInfo);
//	
//	//ProcessInstance
//	List<ProcessInstance> getPagedProcessInstanceList(Integer start, Integer limit , String id ,String query, JSONArray sort, JSONObject queryInfo);
//	
//	long getProcessInstanceCount(Integer start, Integer limit , String id ,String query, JSONArray sort, JSONObject queryInfo);
//	
//	List<ProcessInstance> getAllProcessInstanceList(String id , String query, JSONArray sort, JSONObject queryInfo);
//	
//	//Task
//	List<Task> getPagedTaskInstanceList(Integer start, Integer limit , String id ,String query, JSONArray sort, JSONObject queryInfo);
//	
//	long getTaskCount(Integer start, Integer limit , String id ,String query, JSONArray sort, JSONObject queryInfo);
//	
//	List<Task> getAllTaskList(String id , String query, JSONArray sort, JSONObject queryInfo);

}
