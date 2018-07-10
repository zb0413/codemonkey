package com.codemonkey.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.BaseDictionary;
import com.codemonkey.service.BaseDictionaryService;

@Controller
@RequestMapping("/ext/baseDictionaryList/**")
public class BaseDictionaryListController extends AbsListExtController<BaseDictionary>{

	@Autowired private BaseDictionaryService baseDictionaryService;

	@Override
	protected BaseDictionaryService service() {
		return baseDictionaryService;
	}
}
