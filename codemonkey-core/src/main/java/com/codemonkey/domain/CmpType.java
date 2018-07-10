package com.codemonkey.domain;

public enum CmpType implements IEnum{
	Column , Field , Menu , Button;

	@Override
	public String getText() {
		return name();
	}

	@Override
	public String getName() {
		return name();
	}

}
