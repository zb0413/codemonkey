package com.codemonkey.controller.${subPackage};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ${entityFullName};
import com.codemonkey.service.${subPackage}.${EntityName}Service;
import com.codemonkey.web.controller.AbsFormExtController;

@Controller
@RequestMapping("/ext/${entityName}/**")
public class ${EntityName}FormController extends AbsFormExtController<${EntityName}>{

	@Autowired private ${EntityName}Service ${entityName}Service;

	@Override
	protected ${EntityName}Service service() {
		return ${entityName}Service;
	}
}
