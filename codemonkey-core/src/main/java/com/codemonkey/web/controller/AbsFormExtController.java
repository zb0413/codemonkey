package com.codemonkey.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.IEntity;
import com.codemonkey.domain.UrlPermission;
import com.codemonkey.error.SysError;
import com.codemonkey.security.AppResourceHelper;
import com.codemonkey.utils.ClassHelper;

@Controller
public abstract class AbsFormExtController<T extends IEntity> extends AbsExtController<T>{

	public static final Long NEW_ENTITY_ID = -1L;
	
	@RequestMapping(value="show")
    public String show(@RequestParam(required=false) JSONObject readParams , 
    		@RequestParam(required=false) JSONObject defaultProps , 
    		@RequestParam(required=false) Integer id , ModelMap modelMap) {
		if(readParams != null){
			modelMap.put("readParams", readParams);
		}else if(id != null){
			JSONObject json = new JSONObject();
			json.put("id", id);
			modelMap.put("readParams", json);
		}
		
		if(defaultProps != null){
			modelMap.put("defaultProps", defaultProps);
		}else{
			modelMap.put("defaultProps", new JSONObject());
		}
		
		updateModelMap(modelMap);
		
    	return getShowView();
    }
	
	protected void updateModelMap(ModelMap modelMap) {
		
	}

	protected String getShowView(){
		String prefix = StringUtils.uncapitalize(getType().getSimpleName());
    	return prefix + "Form";
	}
	
	//----------------------
    // create
    //----------------------
    @RequestMapping("create")
    @ResponseBody
    public String create(ServletRequest request) {
    	return handleUpdate(request.getParameterMap());
    }

	//----------------------
    // read
    //----------------------
    @RequestMapping("read")
    @ResponseBody 
    public String read(@RequestParam Long id , @RequestParam(required = false) JSONObject defaultProps) {
    	return handleRead(id , defaultProps);
    }

    protected String handleRead(Long id , JSONObject defaultProps) {
    	
		T t = null;
    	if(NEW_ENTITY_ID.equals(id) || id == null){
    		t = service().createEntity();
    		if(defaultProps != null){
    			ClassHelper.build(defaultProps, t, getCcService());
    		}
    	}else{
    		t = service().get(id);
    		if(t == null){
    			throw new SysError("该数据已被删除，请重新查询！");
    		}
    	}
    	
    	JSONObject result = buildJson(t);
    	return result(result);
	}
    
    protected JSONObject buildJson(T t){
    	return jsonResult(t);
    }

	//----------------------
    // update
    //----------------------
	@RequestMapping("update")
    @ResponseBody
	public String update(ServletRequest request){
		Map<String, String[]> params = request.getParameterMap();
		return handleUpdate(params);
	}

	public List<AppPermission> regAppPermissions(){
		List<UrlPermission> list = AppResourceHelper.formPermissions(getType());
		return convertToAppPermissionList(list);
	}
}
