package com.codemonkey.test.workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.utils.SysUtils;
import com.codemonkey.workflow.WorkflowService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(locations = { 
	"classpath*:spring/applicationContext-*.xml"
})
@ActiveProfiles("test")
@Transactional
public class WorkflowServiceTest {

	@Autowired WorkflowService workflowService;
	
	@Test
	public void testProcess(){
		
		Deployment deployment = workflowService.deployBPMN20FileByClassPath("workflow/MyTestProcess.bpmn20.xml");
		assertNotNull(deployment);
		getLog().info(deployment);
		
		ProcessInstance pInstance = workflowService.startProcessInstance("MyTestProcess");
		assertNotNull(pInstance);
		
		List<Task> tasks = workflowService.getActiveTasks(pInstance.getBusinessKey());
		assertEquals(1 , tasks.size());
		
		getLog().info("============ after start process ===================");
		for(Task t : tasks){
			getLog().info(t.getName());
		}
		getLog().info("============ end ===================================");
		
		getLog().info("complete review task");
		
		workflowService.complete(tasks.get(0).getId() , "Approve");
		
		tasks = workflowService.getActiveTasks("MyTestProcess-1");
		
		getLog().info("============ after complete review task ============");
		for(Task t : tasks){
			getLog().info(t.getName());
		}
		getLog().info("============ end ===================================");
		assertEquals(1 , tasks.size());
		
		getLog().info("complete Approve task");
		workflowService.complete(tasks.get(0).getId() , "Approve");
		tasks = workflowService.getActiveTasks("MyTestProcess-1");
		
		getLog().info("============ after complete Approve task ===========");
		for(Task t : tasks){
			getLog().info(t.getName());
		}
		getLog().info("============ end ===================================");
		assertEquals(0 , tasks.size());
	}

	private Logger getLog() {
		return SysUtils.getLog(getClass());
	}
}
