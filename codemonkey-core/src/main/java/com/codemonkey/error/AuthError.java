package com.codemonkey.error;


public class AuthError extends SysError{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthError(String url) {
		super("Auth Failed for url : " + url);
		setErrorCode("602");
	}
	
}
