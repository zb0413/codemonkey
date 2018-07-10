package com.codemonkey.controller.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.cms.PageTemplate;
import com.codemonkey.service.cms.PageTemplateService;
import com.codemonkey.web.controller.AbsFormExtController;

@Controller
@RequestMapping("/ext/pageTemplate/**")
public class PageTemplateFormController extends AbsFormExtController<PageTemplate>{

	@Autowired private PageTemplateService pageTemplateService;

	@Override
	protected PageTemplateService service() {
		return pageTemplateService;
	}
}
