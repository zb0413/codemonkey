package com.codemonkey.domain.cms;

import com.codemonkey.domain.IEnum;

public enum ArticleCategory implements IEnum{
	文章,招聘, 下载, 最新人才,时间轴;

	@Override
	public String getText() {
		return name();
	}

	@Override
	public String getName() {
		return name();
	}

}
