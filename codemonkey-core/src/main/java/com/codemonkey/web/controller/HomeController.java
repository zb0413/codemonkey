package com.codemonkey.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codemonkey.SysProp;

@Controller
public class HomeController {
	
	@Autowired private SysProp sysProp;

	@RequestMapping("/")
    public String root(HttpServletRequest req , RedirectAttributes ra) {
        return "redirect:" + sysProp.getRootUrl();
    }
	
}
