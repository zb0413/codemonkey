package com.codemonkey.controller.cms;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.BasicTypeIf;
import com.codemonkey.domain.cms.Article;
import com.codemonkey.domain.cms.ArticleSatus;
import com.codemonkey.domain.cms.CarouselFigure;
import com.codemonkey.domain.cms.Menu;
import com.codemonkey.domain.cms.PageTemplate;
import com.codemonkey.domain.cms.PageTemplateType;
import com.codemonkey.error.SysError;
import com.codemonkey.service.UploadEntryService;
import com.codemonkey.service.cms.ArticleService;
import com.codemonkey.service.cms.CarouselFigureService;
import com.codemonkey.service.cms.MenuService;
import com.codemonkey.service.cms.PageTemplateService;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.controller.AbsController;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/cms/router/**")
public class CmsRouterController extends AbsController {

	public static final String CMS_HOME = "cmsHome";

	@Autowired
	private PageTemplateService pageTemplateService;
	@Autowired
	private MenuService menuService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private CarouselFigureService carouselFigureService;

	@Autowired
	private UploadEntryService uploadEntryService;

	@RequestMapping("home")
	public String home(ModelMap modelMap, HttpServletRequest request,
			@RequestParam(required = false) PageTemplate pageTemplate) {
		Map<String, Object> dataModel = new HashMap<String, Object>();

		Menu menu = menuService.findBy("parent.id_isNull");
		List<Menu> menuList = menuService.findAllBy("parent.id", menu.getId());
		dataModel.put("menuList", menuList);

		buildContent(modelMap, PageTemplateType.主页, dataModel, request , pageTemplate);
		return CMS_HOME;
	}

	@RequestMapping("articleList")
	public String articleList(ModelMap modelMap, HttpServletRequest request,
			@RequestParam Menu menu,
			@RequestParam(required = false) PageTemplate pageTemplate){
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("menu", menu);
		
		buildContent(modelMap, PageTemplateType.列表, dataModel, request , pageTemplate);
		return CMS_HOME;
	}

	@RequestMapping("article")
	public String article(ModelMap modelMap, HttpServletRequest request,
			@RequestParam(required = false) Article article,
			@RequestParam(required = false) PageTemplate pageTemplate) {
		Map<String, Object> dataModel = new HashMap<String, Object>();
		
		if(article == null){
			dataModel.put("article", new Article());
		}else{
			dataModel.put("article", article);
		}
		
		buildContent(modelMap, PageTemplateType.文章, dataModel, request , pageTemplate);
		return CMS_HOME;
	}

	

	private void buildContent(ModelMap modelMap, PageTemplateType defaultTemplateType,
			Map<String, Object> dataModel, HttpServletRequest request , PageTemplate pageTemplate) {
		StringWriter out = new StringWriter();
		if (dataModel == null) {
			dataModel = new HashMap<String, Object>();
		}
		
		Menu menu = menuService.findBy("parent.id_isNull");
		List<Menu> menuList = menuService.findAllBy("parent.id", menu.getId());
		dataModel.put("menuList", menuList);
		
		if(pageTemplate == null){
			pageTemplate = pageTemplateService.findBy("type&&defaultTemplate", defaultTemplateType , true);
		}
		
		PageTemplate headerTemplate = pageTemplateService.findBy("type&&defaultTemplate", PageTemplateType.页头 , true);
		PageTemplate footerTemplate = pageTemplateService.findBy("type&&defaultTemplate", PageTemplateType.页脚 , true);
		
		
		dataModel.put("ctx", request.getContextPath());
		dataModel.put("currentUserId", 0L);
		dataModel.put("loginUserName", "");
		dataModel.put("loginUserGroup", 0L);
		
		HttpSession session = request.getSession();

		Enumeration<?> enumeration = session.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			dataModel.put(name, session.getAttribute(name));
		}

		enumeration = request.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			dataModel.put(name, session.getAttribute(name));
		}

		dataModel.get("article");
		
		try {
			Template content = new Template("content", pageTemplate.getDescription(), 
					new Configuration(Configuration.VERSION_2_3_0));
			content.process(dataModel, out);
			
			modelMap.put("content", out.toString());
			
			if(headerTemplate != null){
				out = new StringWriter();
				Template header = new Template("pageHeader", headerTemplate.getDescription(), 
						new Configuration(Configuration.VERSION_2_3_0));
				header.process(dataModel, out);
				modelMap.put("pageHeader", out.toString());
			}
			
			if(footerTemplate != null){
				out = new StringWriter();
				Template footer = new Template("pageFooter", footerTemplate.getDescription(), 
						new Configuration(Configuration.VERSION_2_3_0));
				footer.process(dataModel, out);
				modelMap.put("pageFooter", out.toString());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			throw new SysError("系统内部错误");
		}
	}
	

	
	@RequestMapping("getArticles")
	@ResponseBody
	public String getArticles(@RequestParam Menu menu) {

		JSONObject result = new JSONObject();

		List<Article> articlelist = articleService.findAllBy(
				"menu.id&&topFlag&&status-OrderBy-publishedDate_DESC", menu.getId(),
				BasicTypeIf.是, ArticleSatus.已发布);
		JSONArray array = new JSONArray();
		if (SysUtils.isNotEmpty(articlelist)) {
			for (Article p : articlelist) {
				array.put(p.listJson());
			}
		}
		// 返回明细
		result.put("articlelist", array);

		return result(result);
	}

	@RequestMapping("getSlider")
	@ResponseBody
	public String getSlider() {

		JSONObject result = new JSONObject();

		List<CarouselFigure> figurelist = carouselFigureService.findAll();
		JSONArray array = new JSONArray();
		if (SysUtils.isNotEmpty(figurelist)) {
			for (CarouselFigure carouselFigure : figurelist) {
				array.put(carouselFigure.listJson());
			}
		}
		// 返回明细
		result.put("figurelist", array);

		return result(result);
	}
	
	

}
