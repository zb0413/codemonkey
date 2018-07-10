package com.codemonkey.domain;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tomcat.util.codec.binary.Base64;
//import org.apache.shiro.crypto.RandomNumberGenerator;
//import org.apache.shiro.crypto.SecureRandomNumberGenerator;
//import org.apache.shiro.crypto.hash.Sha256Hash;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.annotation.SkipBuild;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Audited
public class AppUser extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Label("用户名")
	private String username;
	@Label("邮箱")
	private String email;
	@Label("密码")
	@SkipBuild
	private String password;
	@Label("盐值")
	private String salt;
	@Label("手机号码")
	private String workerMobilephone;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="app_user_roles", 
		joinColumns={@JoinColumn(name="app_user")},
		inverseJoinColumns={@JoinColumn(name="roles")})
	@NotAudited
	private Set<AppRole> roles = new HashSet<AppRole>();

	@Enumerated(EnumType.STRING)
	@Label("状态")
	private Status status = Status.启用;
	
	@ManyToOne
	@Label("用户组")
	private AppUserGroup appUserGroup;
	
	public static String generateSalt(){
		SecureRandom secureRandom = new SecureRandom();
		byte[] bytes = new byte[128];
		secureRandom.nextBytes(bytes);
		String salt = Base64.encodeBase64String(bytes);
		
		return salt;
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("username", OgnlUtils.stringValue("username", this));
		//jo.put("password", OgnlUtils.stringValue("password", this));
		jo.put("firstName", OgnlUtils.stringValue("firstName", this));
		jo.put("lastName", OgnlUtils.stringValue("lastName", this));
		jo.put("email", OgnlUtils.stringValue("email", this));
		jo.put("status", OgnlUtils.stringValue("status", this));
		jo.put("appUserGroup", OgnlUtils.stringValue("appUserGroup.id", this));
		jo.put("workerMobilephone", OgnlUtils.stringValue("workerMobilephone", this));
		return jo;
	}
	
	@Override
	public JSONObject detailJson() {
		JSONObject jo = super.detailJson();
		
		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(roles)){
			for(AppRole role : roles){
				ja.put(role.listJson());
			}
		}
		jo.put("roles", ja);
		return jo;
	}
	
	public Set<AppPermission> getPermissions(){
		Set<AppRole> appRoles = getRoles();
		Set<AppPermission> permissions = new HashSet<AppPermission>();
		if(CollectionUtils.isEmpty(appRoles)){
			return permissions;
		}
		
		if(getAppUserGroup() != null && CollectionUtils.isNotEmpty(getAppUserGroup().getRoles())){
			appRoles.addAll(getAppUserGroup().getRoles());
		}
		
		for(AppRole role : appRoles){
			if(CollectionUtils.isNotEmpty(role.getUrlPermissions())){
				permissions.addAll(role.getUrlPermissions());
			}
		}
		
		return permissions;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<AppRole> getRoles() {
		
		Set<AppRole> appRoles = new HashSet<AppRole>();
		if(CollectionUtils.isNotEmpty(roles)){
			appRoles.addAll(roles);
		}
		
		if(this.getAppUserGroup() != null && CollectionUtils.isNotEmpty(this.getAppUserGroup().getRoles())){
			appRoles.addAll(this.getAppUserGroup().getRoles());
		}
		
		return appRoles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AppUserGroup getAppUserGroup() {
		return appUserGroup;
	}

	public void setAppUserGroup(AppUserGroup appUserGroup) {
		this.appUserGroup = appUserGroup;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public void addAppRole(AppRole appRole1) {
		roles.add(appRole1);
	}

	public void clearAppRoles() {
		if(roles != null){
			roles.clear();
		}
	}

	public boolean isAdmin() {
		if("admin".equals(getUsername())){
			return true;
		}
		return false;
	}
	
	public String getWorkerMobilephone() {
		return this.workerMobilephone;
	}
	
	public void setWorkerMobilephone(String value) {
		this.workerMobilephone = value;
	}

}
