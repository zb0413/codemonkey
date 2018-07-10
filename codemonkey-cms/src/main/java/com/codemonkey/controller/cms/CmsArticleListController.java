package com.codemonkey.controller.cms;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.cms.Article;
import com.codemonkey.service.cms.ArticleService;
import com.codemonkey.utils.HqlHelper;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/cms/articleList/**")
public class CmsArticleListController extends AbsListExtController<Article>{

	@Autowired private ArticleService articleService;

	@Override
	protected ArticleService service() {
		return articleService;
	}
	
	//----------------------
    // read
    //----------------------
    @RequestMapping(value="myArticles")
    @ResponseBody 
    public String myArticles(@RequestParam(required = false) Integer page , 
    		@RequestParam(required = false , defaultValue = "0") Integer start , 
    		@RequestParam(required = false , defaultValue = "25") Integer limit ,
    		@RequestParam(required = false) String query,
    		@RequestParam(required = false , defaultValue="[]") JSONArray sort,
    		@RequestParam(required = false , defaultValue="{}") JSONObject queryInfo ,
    		@RequestParam(required = false , defaultValue= "true") boolean pageable) {
    	
    	JSONObject sort1 = new JSONObject();
		sort1.put(HqlHelper.PROPERTY, "publishedDate");
		sort1.put(HqlHelper.DIRECTION, HqlHelper.DESC);
		sort.put(sort1);
    	
    	if(pageable){
    		return handleRead(start, limit, null, query, sort, queryInfo);
    	}
    	return handleRead(null, query, sort, queryInfo );
    }
    
	
}
