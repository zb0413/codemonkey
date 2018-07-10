package com.codemonkey.controller.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.cms.PageTemplate;
import com.codemonkey.service.cms.PageTemplateService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/pageTemplateList/**")
public class PageTemplateListController extends AbsListExtController<PageTemplate>{

	@Autowired private PageTemplateService pageTemplateService;

	@Override
	protected PageTemplateService service() {
		return pageTemplateService;
	}
	
}
