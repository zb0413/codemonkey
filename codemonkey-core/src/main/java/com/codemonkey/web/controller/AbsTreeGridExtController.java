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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.IEntity;
import com.codemonkey.domain.TreeEntity;
import com.codemonkey.tree.TreeUtils;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.OgnlUtils;
import com.codemonkey.utils.SysUtils;

@Controller
public abstract class AbsTreeGridExtController<T extends IEntity> extends AbsExtController<T>{

	protected String getIdField() {
		return "id";
	}
	
	protected String getParentField() {
		return "parent";
	}
	
	//----------------------
    // read
    //----------------------
    @RequestMapping(value="read")
    @ResponseBody 
    public String read(
    		@RequestParam(required = false) Long id,
    		@RequestParam(required = false) String query,
    		@RequestParam(required = false , defaultValue="[]") JSONArray sort,
    		@RequestParam(required = false , defaultValue="{}") JSONObject queryInfo,
    		@RequestParam(required = false , defaultValue="false") Boolean checkable) {
    	
    	return handleRead(id , query, sort, queryInfo , checkable);
    }

	protected String handleRead(Long id ,String query, JSONArray sort, JSONObject queryInfo , Boolean checkable) {
		List<T> list = new ArrayList<T>();
		
		if(id != null){
			T t = service().get(id);
			return result(t);
		}
    	
    	if(StringUtils.isNotBlank(query)){
    		
			String queryValue = '%' + SysUtils.decode(query) + '%';
			list = service().findAllBy("name_Like" , queryValue);
    		
    	}else if(sort != null || queryInfo != null){
    		
    		if(queryInfo.has(ExtConstant.ID)){
    			T t = service().get(queryInfo.getLong(ExtConstant.ID));
    			return result(t);
    		}else{
    			JSONObject queryAndSort = new JSONObject().put(ExtConstant.SORT, sort).put(ExtConstant.QUERY, queryInfo);
        		list = service().findByQueryInfo(queryAndSort);
    		}
    	}
    	return buildJson(list , checkable);
	}
	
	protected String buildJson(List<T> list , Boolean checkable) {
		
		List<TreeEntity> deptList = new ArrayList<TreeEntity>();
		
		if(SysUtils.isNotEmpty(list)){
			for(T t : list){
				TreeEntity te = (TreeEntity) t;
				if(!existed(te , deptList)){
					deptList.add(te);
				}
				addParent(te , deptList);
			}
		}
		return TreeUtils.buildTree(deptList , checkable).toString();
	}

	private void addParent(TreeEntity t, List<TreeEntity> deptList) {
		
		TreeEntity parent = t.getParent();
		
		while(parent != null){
			if(!existed(parent , deptList)){
				deptList.add((TreeEntity)parent);
			}
			parent = parent.getParent();
		}
	}

	private boolean existed(TreeEntity parent, List<TreeEntity> deptList) {
		
		if(SysUtils.isEmpty(deptList)){
			return false;
		}
		
		for(IEntity dept : deptList){
			if(OgnlUtils.stringValue(getIdField(), dept).equals(OgnlUtils.stringValue(getIdField(), parent))){
				return true;
			}
		}
		return false;
	}
	
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
	
}
