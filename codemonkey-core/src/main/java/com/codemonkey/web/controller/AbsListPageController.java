package com.codemonkey.web.controller;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codemonkey.domain.IEntity;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
public abstract class AbsListPageController<T extends IEntity> extends AbsListExtController<T>{

	@RequestMapping(value="show")
    public String show(@RequestParam(defaultValue = "{}") JSONObject searchParams , ModelMap modelMap){
		return handleShow(searchParams, modelMap);
	}

	protected String handleShow(JSONObject searchParams, ModelMap modelMap) {
		modelMap.put("searchParams", searchParams);
		return getViewName();
	}

	private String getViewName() {
		return StringUtils.uncapitalize(getType().getSimpleName()) + "List";
	}

}
