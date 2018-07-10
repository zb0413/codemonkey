package com.codemonkey.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.CmpPermission;
import com.codemonkey.domain.FunctionNode;
import com.codemonkey.domain.IEnum;
import com.codemonkey.domain.SecurityComponent;
import com.codemonkey.domain.UiModule;
import com.codemonkey.domain.UiModuleHolder;
import com.codemonkey.error.AuthError;
import com.codemonkey.error.BadObjVersionError;
import com.codemonkey.error.SessionTimeoutError;
import com.codemonkey.error.SysError;
import com.codemonkey.error.ValidationError;
import com.codemonkey.service.FunctionNodeService;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;


public abstract class AbsController implements SecurityController {
	
	private Class<?> type;
	
	private Logger log;
	
	public static final String LOCALE = "locale";
	
	public static final String DEFAULT_LOCALE = "zh_CN";
	
	@Autowired private FunctionNodeService functionNodeService;
	
	public AbsController(){
		this.type = ClassHelper.getSuperClassGenricType(getClass());
		log = SysUtils.getLog(getClass());
	}
	
	public String result(JSONObject json) {
		JSONObject result = new JSONObject();
		result.put(ExtConstant.DATA, json);
		result.put(ExtConstant.SUCCESS, true);
		return result.toString();
	}
	
	public String result(JSONArray json) {
		JSONObject result = new JSONObject();
		result.put(ExtConstant.DATA, json);
		result.put(ExtConstant.SUCCESS, true);
		return result.toString();
	}
	
	public JSONObject parseJson(Map<String, String[]> body){
		
		if(body == null){
			return new JSONObject();
		}
		try {
			JSONObject params = new JSONObject();
			Set<String> keys = body.keySet();
			
			for(String key : keys){
				String[] values = body.get(key);
				if(values.length == 1){
					
					putValue(params, key, values[0]);
					
				}else if(values.length > 1){
					
					JSONArray array = new JSONArray();
					for(int i = 0 ; i < values.length ; i++){
						array.put(values[i]);
					}
					params.put(key, array);
				}
			}
			
			return params;
			
		} catch (ParseException e) {
			errorResult(e);
			e.printStackTrace();
		}
		return null;
	}

	private void putValue(JSONObject params, String key, String value)
			throws ParseException {
		if(isJsonObject(value)){
			params.put(key, new JSONObject(SysUtils.decodeURL(value)));
		}else if(isJsonArray(value)){
			params.put(key, new JSONArray(SysUtils.decodeURL(value)));
		}else if(SysUtils.isNotEmpty(value)){
			params.put(key, SysUtils.decodeURL(value));
		}else{
			params.put(key, "");
		}
	}

	public void errorResult(Exception e) {
		JSONObject result = new JSONObject();
		result.put(ExtConstant.SUCCESS, false);
		result.put(ExtConstant.ERROR_MSG, e.getMessage());
	}
	
	public boolean isJson(String body) {
		
		String t = SysUtils.decodeURL(body);
		
		return isJsonObject(t) || isJsonArray(t);
	}

	public boolean isJsonArray(String body) {
		if(SysUtils.isEmpty(body)){
			return false;
		}
		
		String s = body.trim();
		boolean flag = false;
		if(s.startsWith("[") && s.endsWith("]")){
			try {
				new JSONArray(s);
				flag = true;
			} catch (ParseException e) {
				flag = false;
			}
		}
		
		return flag;
	}

	public boolean isJsonObject(String body) {
		if(SysUtils.isEmpty(body)){
			return false;
		}
		
		String s = body.trim();
		boolean flag = false;
		if(s.startsWith("{") && s.endsWith("}")){
			try {
				new JSONObject(s);
				flag = true;
			} catch (ParseException e) {
				flag = false;
			}
		}
		return flag;
	}
	
//	private JSONObject queryParamsToJson(String body) {
//		
//		if(StringUtils.isBlank(body)){
//			return new JSONObject();
//		}
//		
//		try{
//			String[] params = body.split("&");
//			JSONObject json = new JSONObject();
//			for (String param : params) {
//				
//				String[] array = param.split("=");
//				
//				String name = array[0];
//				Object value = new String("");
//				
//				if(array.length > 1){
//					
//					if(isJsonObject(array[1])){
//						value = new JSONObject(SysUtils.decodeURL(array[1]));
//					}else if(isJsonArray(array[1])){
//						value = new JSONArray(SysUtils.decodeURL(array[1]));
//					}else if(SysUtils.isNotEmpty(array[1])){
//						value = SysUtils.decodeURL(array[1]);
//					}
//				}
//				json.put(name, value);
//			}
//			return json;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	@ModelAttribute("ctx")
	public String ctx(HttpServletRequest request) {
		return request.getContextPath();
	}
	
	@ModelAttribute("module")
	public String module(@RequestParam(required= false) String module) {
		if(SysUtils.isNotEmpty(module)){
			return module;
		}
		return "";
	}
	
	@ModelAttribute("uiModules")
	public List<UiModule> uiModules() {
		return UiModuleHolder.getAll();
	}
	
	@ModelAttribute(ExtConstant.PAGE_DATA)
	protected String buildPageData(HttpServletRequest req) {
		
//		updateSessionAttributes(req);
		
		JSONObject pageData = getPageData(req);
		
		syncToPageData(pageData , req.getSession());
		
		return pageData.toString();
	}
	
	private void syncToPageData(JSONObject pageData, HttpSession session) {
		Enumeration<?> enumeration = session.getAttributeNames();
		while(enumeration.hasMoreElements()) {
			String name = (String)enumeration.nextElement();
			Object value = session.getAttribute(name);
			
			if(ClassHelper.isPrimitive(value) || value instanceof JSONObject || value instanceof JSONArray){
				pageData.put(name, session.getAttribute(name));
			}else if(value instanceof IEnum){
				IEnum en = (IEnum) value;
				pageData.put(name, en.getName());
			}
		}
		
	}

	protected void updateSessionAttributes(HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		if(session.getAttribute("ctx") == null){
			session.setAttribute("ctx", req.getContextPath());
		}
		
		if(session.getAttribute("uiModules") == null){
			session.setAttribute("uiModules", buildModulePath(req.getContextPath()));
		}
		
		if(session.getAttribute("functionNodes") == null){
			session.setAttribute("functionNodes", buildFunctionNodes());
		}
		
		if(session.getAttribute("permissions") == null){
			session.setAttribute("permissions", buildPermissions());
		}
		
	}

	protected JSONObject getPageData(HttpServletRequest req) {
		JSONObject pageData = new JSONObject();
		return pageData;
	}
	
	private JSONArray buildPermissions() {
		
		JSONArray cmpPermissions = new JSONArray();
		
		AppUser user = SysUtils.getCurrentUser();
		
		if(user == null){
			return null;
		}
		
		Set<AppRole> roles = user.getRoles();
		
		if(CollectionUtils.isNotEmpty(roles)){
			for(AppRole role : roles){
				Set<CmpPermission> cmpSet = role.getCmpPermissions();
				if(CollectionUtils.isNotEmpty(cmpSet)){
					for(CmpPermission cmp : cmpSet){
						cmpPermissions.put(cmp.listJson());
					}
				}
			}
		}
		
		return cmpPermissions;
	}

	protected JSONArray buildFunctionNodes() {
		JSONArray ja = new JSONArray();
		
		AppUser user =  SysUtils.getCurrentUser();
		
		if(user == null){
			return null;
		}
		
		List<FunctionNode> all = functionNodeService.findAllBy("-OrderBy-sortIndex_ASC");
		Set<AppRole> roles = user.getRoles();
		List<AppRole> roleList = new ArrayList<AppRole>();
		roleList.addAll(roles);
		List<FunctionNode> availableNodes = functionNodeService.getFunctionNodesByRoles(roleList);
		
		for(FunctionNode fn : all){
			if(find(fn , availableNodes)){
				JSONObject jo = fn.detailJson();
				jo.put("available", true);
				ja.put(jo);
			}else{
				JSONObject jo = fn.detailJson();
				jo.put("available", false);
				ja.put(jo);
			}
		}
		return ja;
	}
	
	private boolean find(FunctionNode fn, List<FunctionNode> availableNodes) {
		
		if(SysUtils.isEmpty(availableNodes)){
			return false;
		}
		
		for(FunctionNode t : availableNodes){
			if(t.getId().equals(fn.getId())){
				return true;
			}
		}
		
		return false;
	}
	
	private JSONObject buildModulePath(String ctx) {
		JSONObject jo = new JSONObject();
		List<UiModule> uiModules = UiModuleHolder.getAll();
		
		if(SysUtils.isNotEmpty(uiModules)){
			for(UiModule m : uiModules){
				jo.put( m.getModuleId() , ctx + "/javascript/" + m.getModuleId());
			}
		}
		return jo;
	}

	@ExceptionHandler(SysError.class)
	@ResponseBody
	public String handleSysException(SysError ex, HttpServletRequest request) {
		return ex.json().toString();
	}
	
	@ExceptionHandler(AuthError.class)
	@ResponseBody
	public String handleAuthException(AuthError ex, HttpServletRequest request) {
		return ex.json().toString();
	}
	
	@ExceptionHandler(ValidationError.class)
	@ResponseBody
	public String handleValidationException(ValidationError ex, HttpServletRequest request) {
		return ex.json().toString();
	}
	
	@ExceptionHandler(BadObjVersionError.class)
	@ResponseBody
	public String handleBadVersionException(BadObjVersionError ex, HttpServletRequest request) {
		return ex.json().toString();
	}
	
	@ExceptionHandler(SessionTimeoutError.class)
	@ResponseBody
	public String handleSessionTimeout(SessionTimeoutError ex, HttpServletRequest request) {
		return ex.json().toString();
	}
	
	@Override
	public List<AppPermission> regAppPermissions() {
		return new ArrayList<AppPermission>();
	}
	
	@Override
	public List<SecurityComponent> regSecurityComponents() {
		return new ArrayList<SecurityComponent>();
	}
	
	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
