package com.codemonkey.workflow.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.web.controller.AbsController;

public abstract class GenericListController extends AbsController{
	
	protected abstract String handleRead(Integer start, Integer limit, String id,
			String query, JSONArray sort, JSONObject queryInfo );
	
	protected abstract String handleRead(String id, String query, JSONArray sort, JSONObject queryInfo);
    
	protected abstract String handleDestroy(String body);
	
	//----------------------
    // read
    //----------------------
    @RequestMapping(value="read")
    @ResponseBody 
    public String read(@RequestParam(required = false) Integer page , 
    		@RequestParam(required = false , defaultValue = "0") Integer start , 
    		@RequestParam(required = false , defaultValue = "25") Integer limit ,
    		@RequestParam(required = false) String id,
    		@RequestParam(required = false) String query,
    		@RequestParam(required = false , defaultValue="[]") JSONArray sort,
    		@RequestParam(required = false , defaultValue="{}") JSONObject queryInfo ,
    		@RequestParam(required = false , defaultValue= "true") boolean pageable  ) {
    	
    	if(pageable){
    		return handleRead(start, limit, id, query, sort, queryInfo);
    	}
    	return handleRead(id, query, sort, queryInfo );
    }

	//----------------------
    // destroy
    //---------------------- 
	@RequestMapping("destroy")
    @ResponseBody
	public String destroy(@RequestBody String body){
		return handleDestroy(body);
	}
	
}
