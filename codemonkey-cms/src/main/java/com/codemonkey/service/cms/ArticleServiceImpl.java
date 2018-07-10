package com.codemonkey.service.cms;


import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.cms.Article;
import com.codemonkey.domain.cms.ArticleSatus;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.service.LogicalServiceImpl;
import com.codemonkey.utils.SysUtils;

@Service
public class ArticleServiceImpl extends LogicalServiceImpl<Article> implements ArticleService {

	@Override
	public Article createEntity() {
		Article article = new Article();
		return article;
	}
	
	@Override
	protected Set<FieldValidation> validate(Article entity) {
		Set<FieldValidation> set = super.validate(entity);
		if (SysUtils.isEmpty(entity.getName())) {
			set.add(new FormFieldValidation("name", "文章标题不能为空！"));
		}
		if (entity.getPublishedDate() == null) {
			set.add(new FormFieldValidation("publishedDate", "发布时间不能为空！"));
		}
		
		
		if (entity.getStatus() == null) {
			set.add(new FormFieldValidation("status", "发布状态不能为空！"));
			return set;
		}else if(ArticleSatus.已发布.equals(entity.getStatus())){
			//所属栏目
			if (entity.getMenu() == null) {
				set.add(new FormFieldValidation("menu", "所属栏目不能为空！"));
			}
		}
		
		return set;
	}
	
}
