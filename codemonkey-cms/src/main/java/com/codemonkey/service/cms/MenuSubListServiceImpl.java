package com.codemonkey.service.cms;


import org.springframework.stereotype.Service;

import com.codemonkey.domain.cms.MenuSubList;
import com.codemonkey.service.LogicalServiceImpl;

@Service
public class MenuSubListServiceImpl extends LogicalServiceImpl<MenuSubList> implements MenuSubListService {

	@Override
	public MenuSubList createEntity() {
		MenuSubList menuSubList = new MenuSubList();
		return menuSubList;
	}
	
}
