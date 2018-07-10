package com.codemonkey.controller.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.cms.Menu;
import com.codemonkey.service.cms.MenuService;
import com.codemonkey.web.controller.AbsTreeGridExtController;

@Controller
@RequestMapping("/ext/menuTree/**")
public class MenuTreeController extends AbsTreeGridExtController<Menu>{

	@Autowired private MenuService menuService;

	@Override
	protected MenuService service() {
		return menuService;
	}
	
}
