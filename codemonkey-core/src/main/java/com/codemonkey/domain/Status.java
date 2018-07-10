package com.codemonkey.domain;


public enum Status implements IEnum{

	启用 , 停用;

	public String getText() {
		return getName();
	}

	public String getName() {
		return name();
	}
}
