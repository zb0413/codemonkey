package com.codemonkey.domain;

import com.codemonkey.domain.IEnum;


public enum AuditStatus implements IEnum{

	已审核 , 未审核;

	public String getText() {
		return getName();
	}

	public String getName() {
		return name();
	}
}
