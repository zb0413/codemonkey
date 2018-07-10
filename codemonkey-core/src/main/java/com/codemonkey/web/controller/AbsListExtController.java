package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.IEntity;
import com.codemonkey.security.AppResourceHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.view.ExcelView;

@Controller
public abstract class AbsListExtController<T extends IEntity> extends AbsExtController<T>{
	
	@RequestMapping(value="show")
    public String show(
    		@RequestParam(required = false , defaultValue="[]") JSONArray sort,
    		@RequestParam(required = false , defaultValue="{}") JSONObject queryInfo ,
    		ModelMap modelMap) {
			
		modelMap.put("sort", sort);
		modelMap.put("queryInfo", queryInfo);
    	return getShowView();
    }
	
	protected String getShowView(){
		String prefix = StringUtils.uncapitalize(getType().getSimpleName());
    	return prefix + "List";
	}
	
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

	protected String handleRead(Integer start, Integer limit, String id,
			String query, JSONArray sort, JSONObject queryInfo ) {
		List<T> list = new ArrayList<T>();
    	long total = 0;
    	
    	if(StringUtils.isNotBlank(id)){
    		T t = service().get(Long.valueOf(id));
    		total = 1;
    		list.add(t);
    	}else if(StringUtils.isNotBlank(query)){
    		
			String queryValue = '%' + SysUtils.decode(query) + '%';
			list = service().findAllBy("name_Like" , queryValue);
    		total = service().countBy("name_Like" , queryValue);
    		
    	}else if(sort != null || queryInfo != null){
    		JSONObject queryAndSort = new JSONObject().put(ExtConstant.SORT, sort).put(ExtConstant.QUERY, queryInfo);
    		total = service().countByQueryInfo(queryAndSort);
    		if(total > 0){ 
    			list = service().findByQueryInfo(queryAndSort , start , limit);
    		}
    	}
    	
    	return buildJson(list , total , start , limit);
	}
	
	protected String handleRead(String id, String query, JSONArray sort, JSONObject queryInfo) {
		List<T> list = new ArrayList<T>();
    	long total = 0;
    	
    	if(StringUtils.isNotBlank(id)){
    		T t = service().get(Long.valueOf(id));
    		total = 1;
    		list.add(t);
    	}else if(StringUtils.isNotBlank(query)){
    		
			String queryValue = '%' + SysUtils.decode(query) + '%';
			list = service().findAllBy("name_Like" , queryValue);
    		total = service().countBy("name_Like" , queryValue);
    		
    	}else if(sort != null || queryInfo != null){
    		JSONObject queryAndSort = new JSONObject().put(ExtConstant.SORT, sort).put(ExtConstant.QUERY, queryInfo);
    		total = service().countByQueryInfo(queryAndSort);
    		if(total > 0){
    			list = service().findByQueryInfo(queryAndSort);
    		}
    	}
    	return buildJson(list , total);
	}
    
	//----------------------
    // destroy
    //---------------------- 
	@RequestMapping("destroy")
    @ResponseBody
	public String destroy(ServletRequest request){
		return handleDestroy(request.getParameterMap());
	}

	protected String handleDestroy(Map<String,String[]> body) {
		JSONObject params = parseJson(body);
		
		T t = service().get(params.getLong(ExtConstant.ID));
		if(t != null){
			service().doDelete(params.getLong(ExtConstant.ID));
		}
		return success();
	}
	
	//----------------------
    // export
    //----------------------
	@RequestMapping(value="export")
	public ModelAndView export(ModelMap modelMap , 
			@RequestParam JSONArray cols , 
			@RequestParam(required = false , defaultValue = "") String scope,
			@RequestParam(required = false) Integer start,
			@RequestParam(required = false) Integer limit , 
			@RequestParam(required = false) String fileName,
			@RequestParam(required = false , defaultValue = "[]") JSONArray sort,
    		@RequestParam(required = false , defaultValue = "{}") JSONObject queryInfo){
		List<T> list = null;
		JSONObject queryAndSort = new JSONObject().put(ExtConstant.SORT, sort).put(ExtConstant.QUERY, queryInfo);
		if("".equals(scope)){
			list = service().findByQueryInfo(queryAndSort);
		}else if("当前页".equals(scope)){
			list = service().findByQueryInfo(queryAndSort , start , limit);
		}
		
		List<IEntity> list2 = new ArrayList<IEntity>();
		
		if(CollectionUtils.isNotEmpty(list)){
			list2.addAll(list);
		}
		
		ExcelView view = new ExcelView(cols , list2  , fileName);
		return new ModelAndView(view , modelMap);
	}
	
	public List<AppPermission> regAppPermissions(){
		return convertToAppPermissionList(AppResourceHelper.listPermissions(getType()));
	}
}
