package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.IEntity;
import com.codemonkey.domain.UrlPermission;
import com.codemonkey.service.GenericService;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.converter.CustomConversionService;

@Controller
public abstract class AbsExtController<T extends IEntity> extends AbsController {
	
	@Autowired private CustomConversionService ccService;
	
	protected abstract GenericService<T> service();
	
	public String handleUpdate(Map<String, String[]> body) {
		
		JSONObject params = parseJson(body);
		
		T t = service().doSave(params , getCcService());
		
		return result(service().get(t.getId()));
		
	}
	
	public String result(T t) {
		JSONObject result = new JSONObject();
		result.put(ExtConstant.DATA, jsonResult(t));
		result.put(ExtConstant.SUCCESS, true);
		return result.toString();
	}
	
	public String result(List<T> list) {
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		if(SysUtils.isNotEmpty(list)){
			for(T t : list){
				data.put(jsonResult(t));
			}
		}
		result.put(ExtConstant.DATA, data);
		result.put(ExtConstant.SUCCESS, true);
		return result.toString();
	}
	
	public String success() {
		return result(new JSONObject());
	}
	
	public String error(String message) {
		JSONObject result = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("errorMessage", message);
		result.put(ExtConstant.DATA, data);
		result.put(ExtConstant.SUCCESS, false);
		return result.toString();
	}
	
	public JSONObject jsonResult(T t) {
		return t.detailJson();
	}
	
    protected String buildJson(List<T> list) {
    	JSONObject jo = buildResult(list);
		return jo.toString();
	}
    
    protected String buildJson(List<T> list , long total) {
    	JSONObject jo = buildResult(list);
    	jo.put(ExtConstant.TOTAL_COUNT, total);
		return jo.toString();
	}
    
    protected String buildJson(List<T> list , long total , long start , long limit) {
    	JSONObject jo = buildResult(list);
    	jo.put(ExtConstant.TOTAL_COUNT, total);
    	jo.put("start", start);
    	jo.put("limit", limit);
		return jo.toString();
	}
    
    protected JSONObject buildResult(List<T> list) {
		JSONObject jo = new JSONObject();
    	if(list != null){
    		JSONArray data = new JSONArray();
    		for(T t : list){
    			if(t != null){
    				data.put(buildRecord(t));
    			}
    		}
    		jo.put(ExtConstant.SUCCESS, true);
			jo.put(ExtConstant.DATA, data);
    	}
		return jo;
	}
	
	protected JSONObject buildRecord(T t) {
		return t.listJson();
	}

	protected List<AppPermission> convertToAppPermissionList(List<UrlPermission> list) {
		List<AppPermission> pList = new ArrayList<AppPermission>();
		if(CollectionUtils.isNotEmpty(list)){
			for(UrlPermission p : list){
				pList.add(p);
			}
		}
		return pList;
	}
	
	public CustomConversionService getCcService() {
		return ccService;
	}

	public void setCcService(CustomConversionService ccService) {
		this.ccService = ccService;
	}

}
