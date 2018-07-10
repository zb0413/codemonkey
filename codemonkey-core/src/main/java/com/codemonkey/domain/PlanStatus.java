package com.codemonkey.domain;

public enum PlanStatus implements IEnum{

	未锁定 , 锁定;
	@Override
	public String getText() {
		return name();
	}

	@Override
	public String getName() {
		return name();
	}
}
