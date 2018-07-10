package com.codemonkey.error;


public class SessionTimeoutError extends SysError{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SessionTimeoutError(){
		super("会话过期请重新登录");
		setErrorCode("601");
	}

}
