package com.codemonkey.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.util.StopWatch;

import com.codemonkey.domain.IEntity;
import com.codemonkey.domain.TreeEntity;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;

public class TreeUtils {
	
	private static Logger log = SysUtils.getLog(TreeUtils.class);

	private static TreeNode buildTreeNode(TreeEntity<?> root , Map<TreeEntity<?> , List<TreeEntity<?>>> leafMap , Boolean checkable) {
		TreeNode node = null;
		
		
		List<TreeEntity<?>> subList = leafMap.get(root);
		
		if(SysUtils.isEmpty(subList)){
			ChildNode childNode = new ChildNode();
			setAttributes(root, childNode , checkable);
			node = childNode;
			
		}else{
			ParentNode parentNode = new ParentNode();
			setAttributes(root, parentNode , checkable);
			
			for(TreeEntity<?> p : subList){
				TreeNode treeNode = buildTreeNode(p , leafMap , checkable);
				parentNode.add(treeNode);
			}
			node = parentNode;
		}
		
		return node;
	}

//	@SuppressWarnings("unchecked")
	private static void setAttributes(IEntity<?> root, TreeNode node , Boolean checkable) {
//		JSONObject jo = root.listJson();
//		if(jo != null){
//			node.addAttr("text", jo.getString("name"));
//			Iterator<String> it = jo.keys();
//			while(it.hasNext()){
//				String key = it.next();
//				node.addAttr(key, jo.getString(key));
//			}
//			
//			if(checkable != null && checkable){
//				node.addAttr("checked", false);
//			}
//			
//		}
	}

	private static List<TreeEntity<?>> findList(List<TreeEntity<?>> list, Object nodeValue) {
		
		List<TreeEntity<?>> parentList = new ArrayList<TreeEntity<?>>();
		
		if(SysUtils.isNotEmpty(list)){
			for(TreeEntity<?> obj : list){
				
				if(nodeValue == null){
					if(obj.getParent() == null){
						parentList.add(obj);
					}
				}else{
					if(obj.getParent() != null && obj.getParent().getId().equals(nodeValue)){
						parentList.add(obj);
					}
				}
				
			}
		}
		
		return parentList;
	}

	public static JSONObject buildTree(List<TreeEntity<?>> list){
		return buildTree(list , null);
	}
	
	public static JSONObject buildTree(List<TreeEntity<?>> list , Boolean checkable){
		return buildTree(list , null , checkable);
	}

	private static JSONObject buildTree(List<TreeEntity<?>> list, Object parentValue , Boolean checkable) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		JSONObject root = new JSONObject();
		JSONArray data = new JSONArray();
		
		List<TreeEntity<?>> parentList = findList(list, parentValue);
		
		Map<TreeEntity<?> , List<TreeEntity<?>>> leafMap = buildLeafMap(list);
		
		if(SysUtils.isNotEmpty(parentList)){
			
			for(TreeEntity<?> p : parentList){
				TreeNode node = buildTreeNode(p , leafMap , checkable);
				data.put(node.json());
			}
		}
		
		root.put(ExtConstant.DATA, data);
		root.put(ExtConstant.SUCCESS, true);
		
		stopWatch.stop();
		log.info("build tree" + stopWatch.getTotalTimeMillis() + "ms");
		
		return root;
	}

	private static Map<TreeEntity<?>, List<TreeEntity<?>>> buildLeafMap(List<TreeEntity<?>> list) {
		
		if(SysUtils.isEmpty(list)){
			return null;
		}
		Map<TreeEntity<?> , List<TreeEntity<?>>> leafMap = new HashMap<TreeEntity<?> , List<TreeEntity<?>>>();
		
		for(TreeEntity<?> t : list){
			Object parentValue = t.getId();
			List<TreeEntity<?>> children = findList(list, parentValue);
			leafMap.put(t, children);
		}
		
		return leafMap;
	}
	
}
