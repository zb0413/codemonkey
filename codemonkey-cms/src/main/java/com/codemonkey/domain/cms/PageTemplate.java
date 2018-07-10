package com.codemonkey.domain.cms;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.domain.AbsEE;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("网页模板")
public class PageTemplate extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private PageTemplateType type;
	
	@Label("默认模板")
	@Type(type="true_false")
	private Boolean defaultTemplate = true;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("type", OgnlUtils.stringValue("type", this));
		jo.put("defaultTemplate", OgnlUtils.stringValue("defaultTemplate", this));
		return jo;
	}

	public PageTemplateType getType() {
		return type;
	}

	public void setType(PageTemplateType type) {
		this.type = type;
	}

	public Boolean getDefaultTemplate() {
		return defaultTemplate;
	}

	public void setDefaultTemplate(Boolean defaultTemplate) {
		this.defaultTemplate = defaultTemplate;
	}
	
}
