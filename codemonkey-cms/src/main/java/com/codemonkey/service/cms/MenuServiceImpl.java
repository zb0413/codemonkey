package com.codemonkey.service.cms;


import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.cms.Menu;
import com.codemonkey.domain.cms.MenuSubList;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.service.LogicalServiceImpl;
import com.codemonkey.utils.JsonArrayConverter;
import com.codemonkey.utils.SysUtils;

@Service
public class MenuServiceImpl extends LogicalServiceImpl<Menu> implements MenuService {
	
	@Autowired
	private MenuSubListService menuSubListService ;
	@Override
	public Menu createEntity() {
		Menu menu = new Menu();
		return menu;
	}
	
	@Override
	protected Set<FieldValidation> validate(Menu entity) {
		Set<FieldValidation> set = super.validate(entity);
		if (SysUtils.isEmpty(entity.getName())) {
			set.add(new FormFieldValidation("name", "名称不能为空！"));
		}
		
		if (!isUnique(entity, "name", entity.getName())) {
			set.add(new FormFieldValidation("name", "该名称已经存在，栏目名称不能重复！"));
		}
		
		if (entity.getTemplate() == null) {
			set.add(new FormFieldValidation("template", "使用模板不能为空！"));
		}
		 
		return set;
	}
	
	@Override
	public Menu doSave(JSONObject body,
			FormattingConversionServiceFactoryBean ccService) {
		Menu menu = super.doSave(body, ccService);
		
		// 删除目标明细
		JsonArrayConverter<MenuSubList> converter = new JsonArrayConverter<MenuSubList>();
		List<MenuSubList> detail_deleted = converter.convert(body,
				"detail_deleted", MenuSubList.class, ccService);
		if (SysUtils.isNotEmpty(detail_deleted)) {
			for (MenuSubList menuSubList : detail_deleted) {
				if (menuSubList.getId() != null) {
					menuSubListService.delete(menuSubList.getId());
				}
			}
		}
		// 保存添加或修改的明细
		List<MenuSubList> detail_modified = converter.convert(body,
				"detail_modified", MenuSubList.class, ccService);
		if (SysUtils.isNotEmpty(detail_modified)) {
			for (MenuSubList menuSubList : detail_modified) {
				menuSubList.setMenu(menu);
				menuSubListService.save(menuSubList);
			}
		}
		
		return menu;
	}
	
}
