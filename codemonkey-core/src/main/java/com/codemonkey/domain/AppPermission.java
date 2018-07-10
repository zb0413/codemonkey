package com.codemonkey.domain;

import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;
import org.json.JSONObject;

@Audited
@MappedSuperclass
public class AppPermission extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	AppPermission(){}
	
	public AppPermission(String permission , String description){
		this.setCode(permission);
		this.setDescription(description);
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("permission", getPermission());
		return jo;
	}
	
	public void setPermission(String permission) {
		this.setCode(permission);
	}

	public String getPermission() {
		return getCode();
	}

}
