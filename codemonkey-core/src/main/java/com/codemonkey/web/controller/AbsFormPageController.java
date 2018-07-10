package com.codemonkey.web.controller;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codemonkey.domain.IEntity;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.controller.AbsFormExtController;

@Controller
public abstract class AbsFormPageController<T extends IEntity> extends AbsFormExtController<T>{

	@RequestMapping(value="show")
    public String show(@RequestParam(defaultValue = "{}") JSONObject readParams , ModelMap modelMap){
		return handleShow(readParams, modelMap);
	}
	
	@RequestMapping(value="showTab")
    public String showTab(@RequestParam(defaultValue = "{}") JSONObject readParams , ModelMap modelMap , @RequestParam(required = false) String activeTab ){
		if(SysUtils.isNotEmpty(activeTab)){
			modelMap.addAttribute("activeTab", activeTab);
		}
		return handleShow(readParams, modelMap);
	}

	protected String handleShow(JSONObject readParams, ModelMap modelMap) {
		modelMap.put("readParams", readParams);
		return getViewName();
	}

	private String getViewName() {
		return StringUtils.uncapitalize(getType().getSimpleName()) + "Form";
	}
}
