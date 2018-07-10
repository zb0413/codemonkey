package com.codemonkey.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.mybatis.service.MybatisService;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;


public abstract class AbsMybatisFormController extends AbsMybatisController{
	
	@Autowired private MybatisService mybatisService;
	
	public static final Long NEW_ENTITY_ID = -1L;
	
	public abstract String getReadSqlId();
	
	public abstract String getUpdateSqlId();
	
	public abstract String getInsertSqlId();
	
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
    public String read(@RequestParam Long id) {
    	return handleRead(id);
    }

    protected String handleRead(Long id) {
    	
    	JSONObject result = new JSONObject();
    	
    	if(NEW_ENTITY_ID.equals(id) || id == null){
    		
    	}else{
    		Map<String , Object> params = new HashMap<String , Object>();
    		params.put("id", id);
    		Map<String , Object> map = mybatisService.findOne(getReadSqlId(), params);
    		result = jsonResult(map);
    	}
    	
    	return result(result);
	}
    
	//----------------------
    // update
    //----------------------
	@RequestMapping("update")
    @ResponseBody
	public String update(ServletRequest request){
		
		return handleUpdate(request.getParameterMap());
	}

	private String handleUpdate(Map<String, String[]> body) {
		
		JSONObject params = parseJson(body);
		
		Map<String , Object> map = toQueryMap(params);
		
		String s = params.optString(ExtConstant.ID);
		if(SysUtils.isNotEmpty(s)){
			mybatisService.update(getUpdateSqlId(), map);
		}else{
			mybatisService.insert(getUpdateSqlId(), map);
		}
		
		Map<String , Object> result = mybatisService.findOne(getReadSqlId(), map);
		
		return result(jsonResult(result));
	}

	public List<AppPermission> regAppPermissions(){
		return null;
	}

}
