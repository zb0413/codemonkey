package com.codemonkey.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.mybatis.service.MybatisService;
import com.codemonkey.utils.ExtConstant;


public abstract class AbsMybatisListController extends AbsMybatisController{
	
	
	public static String SUBFIX_COUNT = "_count";
	
	public static String SUBFIX_DELETE = "_delete";
	
	@Autowired private MybatisService mybatisService;
	
	public abstract String getQueryId();
	
	//----------------------
    // read
    //----------------------
    @RequestMapping("read")
    @ResponseBody 
    public String read(@RequestParam(required = false) Integer page , 
    		@RequestParam(required = false , defaultValue = "0") Integer start , 
    		@RequestParam(required = false , defaultValue = "25") Integer limit ,
    		@RequestParam(required = false) String id,
    		@RequestParam(required = false) String query,
    		@RequestParam(required = false , defaultValue="[]") JSONArray sort,
    		@RequestParam(required = false , defaultValue="{}") JSONObject queryInfo ,
    		@RequestParam(required = false , defaultValue= "true") boolean pageable ) {
    	
    	return handleRead(page , start , limit , id , query , sort , queryInfo , pageable );
    	
    }
	
    protected String handleRead(Integer page, Integer start, Integer limit,
			String id, String query, JSONArray sort, JSONObject queryInfo , boolean pageable) {
    	
    	List<Map<String , Object>> list = null;
    	long total = 0;
    	queryInfo.put("start", start);
    	queryInfo.put("limit", limit);
    	queryInfo.put("end", start + limit);
    	queryInfo.put("query", query);
    	
    	JSONObject queryAndSort = new JSONObject().put(ExtConstant.SORT, sort).put(ExtConstant.QUERY, queryInfo);
    	
    	Map<String , Object> param = toQueryMap(queryAndSort);
    	
    	if(pageable){
        	total = mybatisService.count(getQueryId() + SUBFIX_COUNT , param);
        	if(total > 0){
        		list = mybatisService.query(getQueryId(), param);
        	}
        	return buildJson(list , total , start , limit);
    	}else{
    		list = mybatisService.query(getQueryId(), param);
    		total = list.size();
    		return buildJson(list , total , null , null);
    	}
	}

    protected String buildJson(List<Map<String, Object>> list, long total, Integer start, Integer limit) {
		JSONObject jo = buildResult(list);
    	jo.put(ExtConstant.TOTAL_COUNT, total);
    	jo.put("start", start);
    	jo.put("limit", limit);
		return jo.toString();
	}
	
	@RequestMapping("destroy")
    @ResponseBody
	public String destroy(ServletRequest request){
		return handleDestroy(request.getParameterMap());
	}
	
	protected String handleDestroy(Map<String, String[]> body) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject params = parseJson(body);
		map.put(ExtConstant.ID, params.getLong(ExtConstant.ID));
		
		mybatisService.delete(getQueryId() + SUBFIX_DELETE, map);

		return result(new JSONObject());
	}

}
