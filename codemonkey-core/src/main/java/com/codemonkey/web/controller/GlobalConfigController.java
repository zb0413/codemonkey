package com.codemonkey.web.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemonkey.domain.GlobalConfig;
import com.codemonkey.repository.GlobalConfigRepository;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/globalConfig/**")
public class GlobalConfigController {

	@Autowired private GlobalConfigRepository globalConfigRepository;
	@GetMapping("posts")
	@ApiOperation(value="测试swagger", notes="springboot 的查询器查询数据")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "predicate", value = "查询条件", required = false, dataType = "Predicate", paramType = "predicate"),
		@ApiImplicitParam(name = "pageable", value = "分页", required = false, dataType = "Pageable", paramType = "pageable")
	})
	public Object posts(@QuerydslPredicate(root = GlobalConfig.class) Predicate predicate, Pageable pageable) {
	    return globalConfigRepository.findAll(predicate, pageable);
	}
}
