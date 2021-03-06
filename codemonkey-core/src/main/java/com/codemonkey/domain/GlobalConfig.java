package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("系统基础配置")
public class GlobalConfig extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public static final String DEFAULT_USER_PASSWARD = "DEFAULT_USER_PASSWARD";
	
	public static final String IMPORT_INIT_DATA = "IMPORT_INIT_DATA";
	
	public static final String BAK_PATH = "BAK_PATH";
	
	public static final String ORDER_DEPT = "ORDER_DEPT";
	
	public static final String SEL_DEPT = "SEL_DEPT";
	
	public static final String TEMPLATE_PATH = "TEMPLATE_PATH";
	
	public static final String TEMPLATE_NAME = "TEMPLATE_NAME";
	
	public static final String DETAIL_PATH = "DETAIL_PATH";
	
	public static final String DETAIL_NAME = "DETAIL_NAME";
	
	public static final String CHK_FLG = "CHK_FLG";
	
	public static final String CHK = "job";
	
	public static final String UPLOAD_PATH = "UPLOAD_PATH";
	
	public static final String MAX_UPLOAD_FILE_SIZE = "MAX_UPLOAD_FILE_SIZE";

	//科室考核表默认分值
	public static final String DEFAULT_DEPT_DEFAULTPOINTS = "DEFAULT_DEPT_DEFAULTPOINTS";

	//个人考核表默认分值
	public static final String DEFAULT_PERS_DEFAULTPOINTS = "DEFAULT_PERS_DEFAULTPOINTS";

	@Label("值")
	private String value;
	
	@Label("值类型")
	@Enumerated(EnumType.STRING)
	private ValueType valueType;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("code", OgnlUtils.stringValue("code", this));
		jo.put("value", OgnlUtils.stringValue("value", this));
		jo.put("valueType", OgnlUtils.stringValue("valueType", this));
		return jo;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}
}
