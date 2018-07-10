package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.UiPortlet;
import com.codemonkey.service.UiPortletService;

@Controller
@RequestMapping("/ext/uiPortletList/**")
public class UiPortletListController extends AbsBatchedController<UiPortlet>{

	@Autowired private UiPortletService uiPortletService;
	
	@Override
	protected UiPortletService service() {
		return uiPortletService;
	}

}
