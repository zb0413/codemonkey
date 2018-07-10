package com.codemonkey.controller.cms;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.cms.Article;
import com.codemonkey.domain.cms.ArticleCategory;
import com.codemonkey.service.cms.ArticleService;
import com.codemonkey.web.controller.AbsFormExtController;

@Controller
@RequestMapping("/ext/postInfo/**")
public class PostInfoFormController extends AbsFormExtController<Article> {

	@Autowired
	private ArticleService articleService;

	@Override
	protected ArticleService service() {
		return articleService;
	}

	@Override
	public String handleUpdate(Map<String, String[]> body) {

		JSONObject params = parseJson(body);
		params.put("category", ArticleCategory.招聘);

		Article t = articleService.doSave(params, getCcService());

		return result(service().get(t.getId()));
	}
}
