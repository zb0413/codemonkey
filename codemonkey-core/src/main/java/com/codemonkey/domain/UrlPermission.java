package com.codemonkey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.envers.Audited;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.security.RequestType;
import com.codemonkey.utils.OgnlUtils;

@Entity
@DiscriminatorValue("URL_PERMISSION")
@Audited
@Label("操作权限")
public class UrlPermission extends AppPermission{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Label("请求路径")
	private String url;
	
	@Enumerated(EnumType.STRING)
	@Label("请求类型")
	private RequestType requestType;
	
	public UrlPermission(){}
	
	public UrlPermission(String permission , String url , RequestType requestType , String description){
		super(permission , description);
		this.url = url;
		this.requestType = requestType;
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("url", OgnlUtils.stringValue("url", this));
		jo.put("requestType", OgnlUtils.stringValue("requestType", this));
		return jo;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

}
