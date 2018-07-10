package com.codemonkey.error;

import org.json.JSONObject;

import com.codemonkey.utils.ExtConstant;

public class SysError extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorCode = "600";
	
	private String detailMessage;
	
	public SysError(String detailMessage){
		this.detailMessage = detailMessage;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}
	
	public String getErrorKey(){
		return this.getClass().getSimpleName();
	}
	
	public JSONObject json(){
		JSONObject jo = new JSONObject();
		jo.put(ExtConstant.SUCCESS, false);
		jo.put(ExtConstant.ERROR_KEY, getErrorKey());
		jo.put(ExtConstant.ERROR_MSG , getDetailMessage());
		jo.put(ExtConstant.ERROR_CODE , getErrorCode());
		return jo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
