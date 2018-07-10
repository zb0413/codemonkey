package com.codemonkey.controller.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.${EntityName};
import com.codemonkey.service.hospital.${EntityName}Service;
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
