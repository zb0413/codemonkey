package com.codemonkey.domain.cms;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.domain.AbsEE;
import com.codemonkey.domain.Status;
import com.codemonkey.domain.TreeEntity;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("栏目")
public class Menu extends AbsEE implements TreeEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@Label("上级栏目")
	private Menu parent;
	
	private String url;
	
	@Enumerated(EnumType.STRING)
	@Label("启用状态")
	private Status status = Status.启用;
	
	@ManyToOne
	@Label("使用模板")
	private PageTemplate template;
	 

	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("parent", OgnlUtils.stringValue("parent.id", this));
		jo.put("parent_name", OgnlUtils.stringValue("parent.name", this));
		jo.put("url", OgnlUtils.stringValue("url", this));
		jo.put("status", OgnlUtils.stringValue("status", this));
		jo.put("template", OgnlUtils.stringValue("template.id", this));
		jo.put("template_name", OgnlUtils.stringValue("template.name", this));
		
		return jo;
	}

	@Override
	public TreeEntity getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public PageTemplate getTemplate() {
		return template;
	}

	public void setTemplate(PageTemplate template) {
		this.template = template;
	}
}
