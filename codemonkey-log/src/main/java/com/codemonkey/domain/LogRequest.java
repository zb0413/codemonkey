package com.codemonkey.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("操作日志信息")
public class LogRequest extends AbsEE {

	/**
	 * 操作日志信息
	 */
	private static final long serialVersionUID = 1L;

	@Label("操作用户")
	@ManyToOne
	private AppUser opUser;

	@Label("操作时间")
	private Date opTime;

	@Label("操作类")
	private String opClass;
	
	@Label("操作结果")
	@Enumerated(EnumType.STRING)
	private OpResultStatus result ;

	@Label("操作方法")
	private String clsMethod;
	
	@Label("方法描述")
	private String clsMedDesc;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("opUser", OgnlUtils.stringValue("opUser.id", this));
		jo.put("opUser_username", OgnlUtils.stringValue("opUser.username", this));
		jo.put("opTime", OgnlUtils.dateValue("opTime", this , "yyyy-MM-dd HH:mm:ss"));
		jo.put("opClass", OgnlUtils.stringValue("opClass", this));
		jo.put("result", OgnlUtils.stringValue("result", this));
		jo.put("clsMethod", OgnlUtils.stringValue("clsMethod", this));
		jo.put("clsMedDesc", OgnlUtils.stringValue("clsMedDesc", this));
		jo.put("errorMsg", OgnlUtils.stringValue("errorMsg", this));
		
		return jo;
	}

	

	public AppUser getOpUser() {
		return opUser;
	}


	public void setOpUser(AppUser opUser) {
		this.opUser = opUser;
	}



	public Date getOpTime() {
		return opTime;
	}


	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}


	public String getOpClass() {
		return opClass;
	}


	public void setOpClass(String opClass) {
		this.opClass = opClass;
	}


	public OpResultStatus getResult() {
		return result;
	}


	public void setResult(OpResultStatus result) {
		this.result = result;
	}


	public String getClsMethod() {
		return clsMethod;
	}


	public void setClsMethod(String clsMethod) {
		this.clsMethod = clsMethod;
	}


	public String getClsMedDesc() {
		return clsMedDesc;
	}


	public void setClsMedDesc(String clsMedDesc) {
		this.clsMedDesc = clsMedDesc;
	}

}
