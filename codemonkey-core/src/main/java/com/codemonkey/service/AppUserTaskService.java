package com.codemonkey.service;

import java.util.List;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.AppUserTask;

public interface AppUserTaskService extends GenericService<AppUserTask>{

	List<AppUserTask> findTasksByUser(AppUser user);
	
	List<AppUserTask> findTasksByRole(AppRole role);
	
	void doCompleteTask(AppUserTask task);
	
	void doAssignTaskToUser(AppUserTask task , AppUser user);
	
	void doAssignTaskToRole(AppUserTask task , AppRole role);
}
