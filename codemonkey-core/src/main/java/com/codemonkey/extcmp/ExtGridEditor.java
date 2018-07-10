package com.codemonkey.extcmp;

import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

public class ExtGridEditor {

	public static final String TEXT_FIELD = "textfield";
	
	public static final String NUMBER_FIELD = "numberfield";
	
	private String xtype;
	
	public ExtGridEditor(String xtype){
		this.xtype = xtype;
	}
	
	public JSONObject json(){
		JSONObject jo = new JSONObject();
		jo.put("xtype", OgnlUtils.stringValue("xtype", this));
		return jo;
	}
	
	public String getXtype() {
		return xtype;
	}
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	
}
