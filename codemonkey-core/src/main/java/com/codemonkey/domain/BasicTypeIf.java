package com.codemonkey.domain;


public enum BasicTypeIf implements IEnum{

	是 , 否;

	public String getText() {
		return getName();
	}

	public String getName() {
		return name();
	}
}
