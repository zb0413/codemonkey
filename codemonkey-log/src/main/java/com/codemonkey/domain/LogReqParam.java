package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("方法参数信息")
public class LogReqParam extends AbsEE {

	/**
	 * 方法参数信息
	 */
	private static final long serialVersionUID = 1L;

	@Label("操作日志信息")
	@ManyToOne
	private LogRequest logRequest;

	@Label("参数")
	private String param;

	@Label("参数值")
	@Lob
	private String value;
	
	@Label("参数类型")
	private String type;


	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		
		jo.put("logRequest", OgnlUtils.stringValue("logRequest.id", this));
		jo.put("param", OgnlUtils.stringValue("param", this));
		jo.put("value", OgnlUtils.stringValue("value", this));
		jo.put("type", OgnlUtils.stringValue("type", this));
		
		return jo;
	}


	public LogRequest getLogRequest() {
		return logRequest;
	}


	public void setLogRequest(LogRequest logRequest) {
		this.logRequest = logRequest;
	}


	public String getParam() {
		return param;
	}


	public void setParam(String param) {
		this.param = param;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	

}
