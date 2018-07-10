package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.domain.FunctionNode;
import com.codemonkey.service.FunctionNodeService;

@Controller
@RequestMapping("/ext/functionNode/**")
public class FunctionNodeFormController extends AbsFormExtController<FunctionNode> {

	@Autowired private FunctionNodeService functionNodeService;

	@Override
	protected FunctionNodeService service() {
		return functionNodeService;
	}
}
