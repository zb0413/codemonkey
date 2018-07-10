package com.codemonkey.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("系统操作日志")
public class OprationLog extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Label("操作人")
	@ManyToOne
	private AppUser appUser;

	@Label("操作人姓名")
	private String userName;
	
	@Label("操作类型")
	private String oprationType;
	
	@Label("操作时间")
	private Date oprationTime;
	
	@Label("操作描述")
	@Lob
	private String msg;
	
	@Label("实体ID")
	private String entityId;

	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("user", OgnlUtils.stringValue("appUser.id", this));
		jo.put("userName", OgnlUtils.stringValue("userName", this));
		jo.put("user_name", OgnlUtils.stringValue("appUser.name", this));
		jo.put("user_username", OgnlUtils.stringValue("appUser.username", this));
		jo.put("oprationType", OgnlUtils.stringValue("oprationType", this));
		jo.put("oprationTime", OgnlUtils.dateValue("oprationTime", this , "yyyy-MM-dd HH:mm:ss"));
		jo.put("msg", OgnlUtils.stringValue("msg", this));
		jo.put("entityId", OgnlUtils.stringValue("entityId", this));
		return jo;
	}


	public String getOprationType() {
		return oprationType;
	}

	public void setOprationType(String oprationType) {
		this.oprationType = oprationType;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Date getOprationTime() {
		return oprationTime;
	}

	public void setOprationTime(Date oprationTime) {
		this.oprationTime = oprationTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public AppUser getAppUser() {
		return appUser;
	}


	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	
}
