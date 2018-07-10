package com.codemonkey.tree;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ParentNode extends TreeNode{

	private boolean expanded = true;
	
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	@Override
	public JSONObject json(){
		JSONObject jo = super.json();
		jo.put("leaf", false);
		jo.put("expanded", expanded);
		JSONArray childrenJa = new JSONArray();
		if(CollectionUtils.isNotEmpty(children)){
			for(TreeNode c : children){
				childrenJa.put(c.json());
			}
		}
		jo.put("children", childrenJa);
		return jo;
	}
	
	public void add(TreeNode node){
		children.add(node);
	}
	
	public void remove(TreeNode node){
		children.remove(node);
	}
	
	public void clearChildren(){
		children.clear();
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
}
