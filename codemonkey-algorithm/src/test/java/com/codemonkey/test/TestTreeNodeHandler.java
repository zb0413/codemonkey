package com.codemonkey.test;

import com.codemonkey.tree.TreeNode;
import com.codemonkey.tree.TreeNodeHandler;

import lombok.Data;

@Data
public class TestTreeNodeHandler implements TreeNodeHandler{

	private String result = "";
	
	@Override
	public void process(TreeNode node) {
		result += node.getValue().toString();
	}

}
