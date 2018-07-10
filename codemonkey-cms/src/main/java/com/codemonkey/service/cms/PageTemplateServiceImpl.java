package com.codemonkey.service.cms;


import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.cms.PageTemplate;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.service.LogicalServiceImpl;
import com.codemonkey.utils.SysUtils;

@Service
public class PageTemplateServiceImpl extends LogicalServiceImpl<PageTemplate> implements PageTemplateService {

	@Override
	public PageTemplate createEntity() {
		PageTemplate pageTemplate = new PageTemplate();
		return pageTemplate;
	}
	
	@Override
	protected Set<FieldValidation> validate(PageTemplate entity) {
		Set<FieldValidation> set = super.validate(entity);
		if (SysUtils.isEmpty(entity.getName())) {
			set.add(new FormFieldValidation("name", "名称不能为空！"));
		}
		
		if (entity.getType() == null) {
			set.add(new FormFieldValidation("type", "模板类型不能为空！"));
		}
		if (SysUtils.isEmpty(entity.getDescription())) {
			set.add(new FormFieldValidation("description", "模板内容不能为空！"));
		}
		 
		return set;
	}
	
}
