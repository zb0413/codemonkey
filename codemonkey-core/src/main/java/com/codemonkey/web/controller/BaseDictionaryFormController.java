package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.BaseDictionary;
import com.codemonkey.service.BaseDictionaryService;
import com.codemonkey.web.controller.AbsFormExtController;

@Controller
@RequestMapping("/ext/baseDictionary/**")
public class BaseDictionaryFormController extends AbsFormExtController<BaseDictionary>{

	@Autowired private BaseDictionaryService baseDictionaryService;

	@Override
	protected BaseDictionaryService service() {
		return baseDictionaryService;
	}
}
