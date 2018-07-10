package com.codemonkey.tree;

import org.json.JSONObject;

public class ChildNode extends TreeNode{

	@Override
	public JSONObject json(){
		JSONObject jo = super.json();
		jo.put("leaf", true);
		jo.put("cls", "file");
		return jo;
	}
	
}
