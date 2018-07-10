package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

@Entity
@Audited
public class AppUserTaskHistory extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private AppUserTask task;
	
	@ManyToOne
	private AppUser fromAssignee;
	
	@ManyToOne
	private AppUser toAssignee;
	
	@ManyToOne
	private AppRole fromRole;
	
	@ManyToOne
	private AppRole toRole;
	
	@Enumerated(EnumType.STRING)
	private TaskStatus fromTaskStatus;
	
	@Enumerated(EnumType.STRING)
	private TaskStatus toTaskStatus;
	
	private String remark;
	
	@Override
	public JSONObject listJson() {
		JSONObject json = super.listJson();
		json.put("task", OgnlUtils.stringValue("task.id", this));
		json.put("task_name", OgnlUtils.stringValue("task.name", this));
		json.put("fromAssignee", OgnlUtils.stringValue("fromAssignee.id", this));
		json.put("fromAssignee_name", OgnlUtils.stringValue("fromAssignee.name", this));
		json.put("toAssignee", OgnlUtils.stringValue("toAssignee.id", this));
		json.put("toAssignee_name", OgnlUtils.stringValue("toAssignee.name", this));
		json.put("fromRole", OgnlUtils.stringValue("fromRole.id", this));
		json.put("fromRole_name", OgnlUtils.stringValue("fromRole.name", this));
		json.put("toRole", OgnlUtils.stringValue("toRole.id", this));
		json.put("toRole_name", OgnlUtils.stringValue("toRole.name", this));
		json.put("fromTaskStatus", OgnlUtils.stringValue("fromTaskStatus", this));
		json.put("toTaskStatus", OgnlUtils.stringValue("toTaskStatus", this));
		json.put("remark", OgnlUtils.stringValue("remark", this));
		return json;
	}

	public AppUserTask getTask() {
		return task;
	}

	public void setTask(AppUserTask task) {
		this.task = task;
	}

	public AppUser getFromAssignee() {
		return fromAssignee;
	}

	public void setFromAssignee(AppUser fromAssignee) {
		this.fromAssignee = fromAssignee;
	}

	public AppUser getToAssignee() {
		return toAssignee;
	}

	public void setToAssignee(AppUser toAssignee) {
		this.toAssignee = toAssignee;
	}

	public AppRole getFromRole() {
		return fromRole;
	}

	public void setFromRole(AppRole fromRole) {
		this.fromRole = fromRole;
	}

	public AppRole getToRole() {
		return toRole;
	}

	public void setToRole(AppRole toRole) {
		this.toRole = toRole;
	}

	public TaskStatus getFromTaskStatus() {
		return fromTaskStatus;
	}

	public void setFromTaskStatus(TaskStatus fromTaskStatus) {
		this.fromTaskStatus = fromTaskStatus;
	}

	public TaskStatus getToTaskStatus() {
		return toTaskStatus;
	}

	public void setToTaskStatus(TaskStatus toTaskStatus) {
		this.toTaskStatus = toTaskStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
