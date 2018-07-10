package com.codemonkey.domain.cms;

import com.codemonkey.domain.IEnum;

public enum PageTemplateType implements IEnum{
	主页, 列表, 文章, 其他,页头,页脚;

	@Override
	public String getText() {
		return name();
	}

	@Override
	public String getName() {
		return name();
	}

}
