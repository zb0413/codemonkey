package com.codemonkey.domain;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

public class GanttTask {

	/**
	 * 甘特图
	 */
	
	@Label("id")
	private Long id;
	
	@Label("序号")
	private Long orderId;
	
	@Label("上级节点")
	private Long parentId;
	
	@Label("开始日期")
	private String start;
	
	@Label("结束日期")
	private String end;
	
	@Label("完成百分比")
	private Double percentComplete;
	
	@Label("标题")
	private String title;
	
	@Label("")
	private Boolean summary;
	
	@Label("默认展开")
	private Boolean expanded;
	
	public JSONObject listJson() {
		JSONObject jo = new JSONObject();
		jo.put("id", OgnlUtils.stringValue("id", this));
		jo.put("orderId", OgnlUtils.stringValue("orderId", this));
		jo.put("parentId", OgnlUtils.stringValue("parentId", this));
		jo.put("title", OgnlUtils.stringValue("title", this));
		jo.put("start", OgnlUtils.stringValue("start", this));
		jo.put("end", OgnlUtils.stringValue("end", this));
		jo.put("percentComplete", OgnlUtils.stringValue("percentComplete", this));
		jo.put("summary", OgnlUtils.stringValue("summary", this));
		jo.put("expanded", OgnlUtils.stringValue("expanded", this));
		return jo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Double getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(Double percentComplete) {
		this.percentComplete = percentComplete;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getSummary() {
		return summary;
	}

	public void setSummary(Boolean summary) {
		this.summary = summary;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
	
 
}
