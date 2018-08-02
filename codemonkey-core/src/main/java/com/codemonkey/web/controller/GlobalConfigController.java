package com.codemonkey.web.controller;

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
	public Object posts(@QuerydslPredicate(root = GlobalConfig.class) Predicate predicate, Pageable pageable) {
	    return globalConfigRepository.findAll(predicate, pageable);
	}
}
