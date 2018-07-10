package com.codemonkey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
@DiscriminatorValue("CMP_PERMISSION")
@Audited
@Label("控件可见性")
public class CmpPermission extends AppPermission{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
	@Label("操作权限")
	private CmpPermissionType cmpPermissionType;
	
	@Label("控件ID")
	@ManyToOne
	@NotAudited
	private SecurityComponent component;
	
	public CmpPermission(){}
	
	public CmpPermission(SecurityComponent component , CmpPermissionType cmpPermissionType){
		this.cmpPermissionType = cmpPermissionType;
		this.setComponent(component);
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("cmpPermissionType", OgnlUtils.stringValue("cmpPermissionType", this));
		jo.put("component", OgnlUtils.stringValue("component.id", this));
		jo.put("component_code", OgnlUtils.stringValue("component.code", this));
		jo.put("component_type", OgnlUtils.stringValue("component.cmpType", this));
		jo.put("component_description", OgnlUtils.stringValue("component.description", this));
		return jo;
	}
	
	public CmpPermissionType getCmpPermissionType() {
		return cmpPermissionType;
	}

	public void setCmpPermissionType(CmpPermissionType cmpPermissionType) {
		this.cmpPermissionType = cmpPermissionType;
	}

	public SecurityComponent getComponent() {
		return component;
	}

	public void setComponent(SecurityComponent component) {
		this.component = component;
	}

}
