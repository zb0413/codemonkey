package com.codemonkey.domain;


public enum OpResultStatus implements IEnum{

	成功 , 失败;

	public String getText() {
		return getName();
	}

	public String getName() {
		return name();
	}
}
