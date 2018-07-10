package com.codemonkey.security;

import com.codemonkey.domain.IEnum;

public enum RequestType implements IEnum{

	HTML , JSON;

	public String getText() {
		return getName();
	}

	public String getName() {
		return name();
	}
}
