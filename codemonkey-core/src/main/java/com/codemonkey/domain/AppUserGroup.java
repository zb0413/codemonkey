package com.codemonkey.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.envers.Audited;
import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Audited
public class AppUserGroup extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<AppRole> roles = new HashSet<AppRole>();
	
	@Override
	public JSONObject detailJson() {
		JSONObject jo = super.detailJson();
		
		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(getRoles())){
			for(AppRole role : getRoles()){
				ja.put(role.listJson());
			}
		}
		jo.put("appRoles", ja);
		return jo;
	}

	public Set<AppRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<AppRole> roles) {
		this.roles = roles;
	}
	
}
