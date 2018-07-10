package com.codemonkey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.AppUserTask;
import com.codemonkey.domain.AppUserTaskHistory;
import com.codemonkey.domain.TaskStatus;
import com.codemonkey.error.SysError;

@Service
public class AppUserTaskServiceImpl extends LogicalServiceImpl<AppUserTask> implements AppUserTaskService{

	@Autowired private AppUserTaskHistoryService appUserTaskHistoryService;
	
	@Override
	public AppUserTask createEntity() {
		return new AppUserTask();
	}

	@Override
	public List<AppUserTask> findTasksByUser(AppUser user) {
		if(user == null){
			return null;
		}
		return findAllBy("assignee.id", user.getId());
	}

	@Override
	public List<AppUserTask> findTasksByRole(AppRole role) {
		if(role == null){
			return null;
		}
		return findAllBy("role.id", role.getId());
	}

	@Override
	public void doCompleteTask(AppUserTask task) {
		
		if(task == null){
			throw new SysError(" task is empty ");
		}
		
		AppUserTaskHistory history = appUserTaskHistoryService.createEntity();
		history.setFromTaskStatus(task.getTaskStatus());
		history.setToTaskStatus(TaskStatus.DONE);
		history.setRemark("change status to " + TaskStatus.DONE);
		appUserTaskHistoryService.save(history);
		
		task.setTaskStatus(TaskStatus.DONE);
		save(task);
	}

	@Override
	public void doAssignTaskToUser(AppUserTask task, AppUser toAssignee) {
		if(task == null){
			throw new SysError(" task is empty ");
		}
		
		if(toAssignee == null){
			throw new SysError(" Assignee is empty ");
		}
		AppUserTaskHistory history = appUserTaskHistoryService.createEntity();
		history.setFromAssignee(task.getAssignee());
		history.setToAssignee(toAssignee);
		history.setRemark("assign to user " + toAssignee.getName());
		appUserTaskHistoryService.save(history);
		
		task.setAssignee(toAssignee);
		save(task);
	}

	@Override
	public void doAssignTaskToRole(AppUserTask task, AppRole toRole) {
		if(task == null){
			throw new SysError(" task is empty ");
		}
		
		if(toRole == null){
			throw new SysError(" role is empty ");
		}
		
		AppUserTaskHistory history = appUserTaskHistoryService.createEntity();
		history.setFromRole(task.getRole());
		history.setToRole(toRole);
		history.setRemark("assign to role " + toRole.getName());
		appUserTaskHistoryService.save(history);
		
		task.setRole(toRole);
		save(task);
	}

}
