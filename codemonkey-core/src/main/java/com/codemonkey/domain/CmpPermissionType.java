package com.codemonkey.domain;

public enum CmpPermissionType implements IEnum{
	Hidden , ReadOnly;

	@Override
	public String getText() {
		return name();
	}

	@Override
	public String getName() {
		return name();
	}

}
