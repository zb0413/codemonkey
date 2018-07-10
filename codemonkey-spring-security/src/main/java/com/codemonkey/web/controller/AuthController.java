package com.codemonkey.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/**")
public class AuthController extends AbsAuthController{

}
