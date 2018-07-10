package com.codemonkey.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.codemonkey.utils.SysUtils;

public class UiModuleHolder {

	private static Map<String, UiModule> holder = new HashMap<String, UiModule>();
	
	private static UiModule defaultModlue = null;
	
	public static void add(UiModule module){
		
		if(holder == null){
			holder = new HashMap<String, UiModule>();
		}
		
		holder.put(module.getModuleId() , module);
		
	}
	
	public static List<UiModule> getAll(){
		
		if(holder == null){
			return null;
		}
		
		Set<String> set = holder.keySet();
		
		if(SysUtils.isEmpty(set)){
			return null;
		}
		
		
		List<UiModule> list = new ArrayList<UiModule>();
		
		for(String key : set){
			list.add(holder.get(key));
		}
		
		return list;
		
	}
	
//	//return max priority uimodule
//	public static UiModule getDefault(){
//		UiModule m = null;
//		
//		List<UiModule> list = getAll();
//		
//		if(SysUtils.isNotEmpty(list)){
//			for(UiModule t : list){
//				if(m == null){
//					m = t;
//				}else if(t.getPriority() > m.getPriority()){
//					m = t;
//				}
//			}
//		}
//		return m;
//	}

	public static UiModule getDefaultModlue() {
		return defaultModlue;
	}

	public static void setDefaultModlue(UiModule defaultModlue) {
		UiModuleHolder.defaultModlue = defaultModlue;
	}

	public static UiModule get(String uiModuleId) {
		if(SysUtils.isEmpty(uiModuleId) || holder == null){
			return null;
		}
		return holder.get(uiModuleId);
	}
	
}
