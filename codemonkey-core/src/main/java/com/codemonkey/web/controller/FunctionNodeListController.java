package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.domain.FunctionNode;
import com.codemonkey.service.FunctionNodeService;

@Controller
@RequestMapping("/ext/functionNodeList/**")
public class FunctionNodeListController extends AbsListExtController<FunctionNode> {

	@Autowired private FunctionNodeService functionNodeService;

	@Override
	protected FunctionNodeService service() {
		return functionNodeService;
	}
}
