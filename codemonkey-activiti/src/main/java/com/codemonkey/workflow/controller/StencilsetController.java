package com.codemonkey.workflow.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.utils.ExtConstant;
import com.codemonkey.web.controller.AbsController;
import com.codemonkey.workflow.WorkflowService;

@Controller
@RequestMapping("/ext/stencilset/**")
public class StencilsetController extends AbsController{

	@Autowired private WorkflowService workflowService;
	
	@RequestMapping("read")
	@ResponseBody
	public String read(){
		
		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		BufferedReader br = null;
		try {
			Resource[] resources = patternResolver.getResources("classpath*:public/stencilset.json");
			br = new BufferedReader(new InputStreamReader(resources[0].getInputStream()));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    
			JSONObject json = new JSONObject();
			json.put(ExtConstant.SUCCESS, true);
			json.put(ExtConstant.DATA , everything);
			return json.toString();
			
		} catch(Exception e){
			e.printStackTrace();
		}finally {
		    try {
		    	if(br != null){
		    		br.close();
		    	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
