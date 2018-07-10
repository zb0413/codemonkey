package com.codemonkey.error;

import org.json.JSONObject;

import com.codemonkey.utils.ExtConstant;


public class BadObjVersionError extends SysError{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JSONObject data;
	
	public BadObjVersionError(JSONObject data) {
		super("data has been modified ");
		this.data = data;
		setErrorCode("701");
	}
	
	@Override
	public JSONObject json(){
		JSONObject jo = super.json();
		jo.put(ExtConstant.DATA , getData());
		return jo;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}
	
}
