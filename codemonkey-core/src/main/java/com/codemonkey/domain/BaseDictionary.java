package com.codemonkey.domain;

import javax.persistence.Entity;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("基础码表信息")
public class BaseDictionary extends AbsEE{

	private static final long serialVersionUID = 1L;
	
	@Label("分类")
	private String fieldType;
	
	@Label("码值")
	private String codeValue;
	
	@Label("顺序")
	private Integer sortIndex;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("fieldType", OgnlUtils.stringValue("fieldType", this));
		jo.put("codeValue", OgnlUtils.stringValue("codeValue", this));
		jo.put("sortIndex", OgnlUtils.stringValue("sortIndex", this));
		return jo;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	
}
