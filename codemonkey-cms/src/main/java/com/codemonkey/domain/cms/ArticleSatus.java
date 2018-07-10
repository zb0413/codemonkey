package com.codemonkey.domain.cms;

import com.codemonkey.domain.IEnum;

public enum ArticleSatus implements IEnum{
	已发布, 未发布;

	@Override
	public String getText() {
		return name();
	}

	@Override
	public String getName() {
		return name();
	}

}
