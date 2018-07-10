package com.codemonkey.domain;

import javax.persistence.Entity;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
public class UiPortlet extends AbsEE{

	/**
	 * 组件信息表
	 */
	private static final long serialVersionUID = 1L;
	
	@Label("组件类型")
	private String xtype;
	
	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("xtype", OgnlUtils.stringValue("xtype", this));
		return jo;
	}
	
}
