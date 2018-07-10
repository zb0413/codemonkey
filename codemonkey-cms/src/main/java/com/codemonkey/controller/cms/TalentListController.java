package com.codemonkey.controller.cms;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.cms.Article;
import com.codemonkey.domain.cms.ArticleCategory;
import com.codemonkey.service.cms.ArticleService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/talentList/**")
public class TalentListController extends AbsListExtController<Article> {

	@Autowired
	private ArticleService articleService;

	@Override
	protected ArticleService service() {
		return articleService;
	}

	@Override
	protected String handleRead(Integer start, Integer limit, String id,
			String query, JSONArray sort, JSONObject queryInfo) {

		queryInfo.put("category", ArticleCategory.最新人才);
		return super.handleRead(start, limit, id, query, sort, queryInfo);
	}

}
