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
public class AppUserTask extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private AppUser assignee;
	
	@ManyToOne
	private AppUser creator;
	
	@ManyToOne
	private AppRole role;
	
	@Enumerated(EnumType.STRING)
	private TaskStatus taskStatus;
	
	private String viewClass;
	
	private String readParams;
	
	@Override
	public JSONObject listJson() {
		JSONObject json = super.listJson();
		json.put("assignee", OgnlUtils.stringValue("assignee.id", this));
		json.put("assignee_name", OgnlUtils.stringValue("assignee.name", this));
		json.put("creator", OgnlUtils.stringValue("creator.id", this));
		json.put("creator_name", OgnlUtils.stringValue("creator.name", this));
		json.put("role", OgnlUtils.stringValue("role.id", this));
		json.put("role_name", OgnlUtils.stringValue("role.name", this));
		json.put("taskStatus", OgnlUtils.stringValue("taskStatus", this));
		json.put("viewClass", OgnlUtils.stringValue("taskStatus", this));
		json.put("taskStatus", OgnlUtils.stringValue("readParams", this));
		return json;
	}

	public AppUser getAssignee() {
		return assignee;
	}

	public void setAssignee(AppUser assignee) {
		this.assignee = assignee;
	}

	public AppUser getCreator() {
		return creator;
	}

	public void setCreator(AppUser creator) {
		this.creator = creator;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public AppRole getRole() {
		return role;
	}

	public void setRole(AppRole role) {
		this.role = role;
	}

	public String getViewClass() {
		return viewClass;
	}

	public void setViewClass(String viewClass) {
		this.viewClass = viewClass;
	}

	public String getReadParams() {
		return readParams;
	}

	public void setReadParams(String readParams) {
		this.readParams = readParams;
	}
	
}
