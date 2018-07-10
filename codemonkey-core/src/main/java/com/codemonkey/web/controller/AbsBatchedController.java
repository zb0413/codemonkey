package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.IEntity;

@Controller
public abstract class AbsBatchedController<T extends IEntity> extends AbsListExtController<T>{

	
	//----------------------
    // create
    //----------------------
	@RequestMapping("create")
    @ResponseBody
	public String create(ServletRequest request){
		return handleUpdate(request.getParameterMap());
	}
	
	//----------------------
    // update
    //----------------------
	@RequestMapping("update")
    @ResponseBody
	public String update(ServletRequest request){
		return handleUpdate(request.getParameterMap());
	}
	
	//----------------------
    // Batched Update
    //----------------------
	@RequestMapping("batchedUpdate")
    @ResponseBody
	public String batchedUpdate(ServletRequest request){
		return handleBatchedUpdate(request.getParameterMap());
	}
	
	public String handleBatchedUpdate(Map<String,String[]> body) {
		JSONObject params = parseJson(body);
		
		service().doBatchedSave(params , getCcService());
		
		return success();
	}

	@Override
	protected String handleRead(Integer start, Integer limit, String id,
			String query, JSONArray sort, JSONObject queryInfo) {
		
		if(StringUtils.isNotBlank(id) &&  Long.valueOf(id).equals(AbsFormExtController.NEW_ENTITY_ID)){
			return createNewOne();
		}
		
		return super.handleRead(start, limit, id, query, sort, queryInfo);
	}

	private String createNewOne() {
		List<T> list = new ArrayList<T>();
		list.add(service().createEntity());
		return buildJson(list);
	}
	
	@Override
	protected String handleRead(String id, String query, JSONArray sort,
			JSONObject queryInfo) {
		
		if(StringUtils.isNotBlank(id) &&  Long.valueOf(id).equals(AbsFormExtController.NEW_ENTITY_ID)){
			return createNewOne();
		}
		
		return super.handleRead(id, query, sort, queryInfo);
	}
}
