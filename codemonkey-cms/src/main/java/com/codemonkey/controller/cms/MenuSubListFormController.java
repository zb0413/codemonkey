package com.codemonkey.controller.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.cms.MenuSubList;
import com.codemonkey.service.cms.MenuSubListService;
import com.codemonkey.web.controller.AbsFormExtController;

@Controller
@RequestMapping("/ext/menuSubList/**")
public class MenuSubListFormController extends AbsFormExtController<MenuSubList>{

	@Autowired private MenuSubListService menuSubListService;

	@Override
	protected MenuSubListService service() {
		return menuSubListService;
	}
}
