package com.codemonkey.domain;

import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

public class GanttDependencies {

	/**
	 * 甘特图
	 */
	
	private Long id;

	private Long predecessorId;
	
	private Long successorId;

	private Long type;
	
	public JSONObject listJson() {
		JSONObject jo = new JSONObject();
		jo.put("id", OgnlUtils.stringValue("id", this));
		jo.put("predecessorId", OgnlUtils.stringValue("predecessorId", this));
		jo.put("successorId", OgnlUtils.stringValue("successorId", this));
		jo.put("type", OgnlUtils.stringValue("type", this));

		return jo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPredecessorId() {
		return predecessorId;
	}

	public void setPredecessorId(Long predecessorId) {
		this.predecessorId = predecessorId;
	}

	public Long getSuccessorId() {
		return successorId;
	}

	public void setSuccessorId(Long successorId) {
		this.successorId = successorId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}


 
}
