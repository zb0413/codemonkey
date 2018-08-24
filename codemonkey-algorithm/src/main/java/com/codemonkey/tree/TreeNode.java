package com.codemonkey.tree;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TreeNode {
	
	Object value;
	
	List<TreeNode> children = new ArrayList<TreeNode>();
	
}
