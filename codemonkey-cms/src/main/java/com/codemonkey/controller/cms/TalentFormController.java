package com.codemonkey.controller.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.cms.Article;
import com.codemonkey.service.cms.ArticleService;
import com.codemonkey.web.controller.AbsFormExtController;

@Controller
@RequestMapping("/ext/talent/**")
public class TalentFormController extends AbsFormExtController<Article> {

	@Autowired
	private ArticleService articleService;

	@Override
	protected ArticleService service() {
		return articleService;
	}

}
