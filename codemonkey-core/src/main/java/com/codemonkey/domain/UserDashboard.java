package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
public class UserDashboard extends AbsEE{

	/**
	 * 工作台信息表
	 */
	private static final long serialVersionUID = 1L;
	
	@Label("用户")
	@ManyToOne
	private AppUser appUser;
	
	@Label("组件")
	@ManyToOne
	private UiPortlet uiPortlet;
	
	@Label("用户配置")
	private String userConfig;
	
	@Label("组件列号")
	private Integer colIndex;
	
	@Label("组件行号")
	private Integer sortIndex;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("appUser", OgnlUtils.stringValue("appUser.id", this));
		jo.put("appUser_username", OgnlUtils.stringValue("appUser.username", this));
		jo.put("uiPortlet", OgnlUtils.stringValue("uiPortlet.id", this));
		jo.put("uiPortlet_name", OgnlUtils.stringValue("uiPortlet.name", this));
		jo.put("uiPortlet_xtype", OgnlUtils.stringValue("uiPortlet.xtype", this));
		jo.put("colIndex", OgnlUtils.stringValue("colIndex", this));
		jo.put("sortIndex", OgnlUtils.stringValue("sortIndex", this));
		jo.put("userConfig", OgnlUtils.stringValue("userConfig", this));
		return jo;
	}
	
	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public UiPortlet getUiPortlet() {
		return uiPortlet;
	}

	public void setUiPortlet(UiPortlet uiPortlet) {
		this.uiPortlet = uiPortlet;
	}

	public String getUserConfig() {
		return userConfig;
	}

	public void setUserConfig(String userConfig) {
		this.userConfig = userConfig;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public Integer getColIndex() {
		return colIndex;
	}

	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}
	 
}
