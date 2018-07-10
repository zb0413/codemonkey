package com.codemonkey.tree;

import org.json.JSONObject;

public abstract class TreeNode {

	private JSONObject jo = new JSONObject();
	
	public JSONObject addAttr(String key , Object value){
		return jo.put(key, value);
	}
	
	public Object getAttr(String key){
		return jo.get(key);
	}
	
	public JSONObject removeAttr(String key){
		jo.remove(key);
		return jo;
	}
	
	public JSONObject json(){
		return jo;
	}

}
