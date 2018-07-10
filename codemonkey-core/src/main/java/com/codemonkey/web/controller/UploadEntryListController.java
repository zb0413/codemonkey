package com.codemonkey.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.UploadEntry;
import com.codemonkey.service.UploadEntryService;

@Controller
@RequestMapping("/ext/uploadEntryList/**")
public class UploadEntryListController extends AbsListExtController<UploadEntry>{

	@Autowired private UploadEntryService uploadEntryService;
	
	@Override
	protected UploadEntryService service() {
		return uploadEntryService;
	}
	
}
