package com.codemonkey.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class TreeSolver {

	//深度优先
	public void depthFirst(TreeNode treeNode , TreeNodeHandler handler) {

	    Stack<TreeNode> nodeStack = new Stack<TreeNode>();
	    
	    nodeStack.add(treeNode);
	    
	    while (!nodeStack.isEmpty()) {
	        TreeNode node = nodeStack.pop();
	        
	        List<TreeNode> children = getChildren(node);
	        
	        if (children != null && !children.isEmpty()) {
	            for (TreeNode child : children) {
	                nodeStack.push(child);
	            }
	        }
	        handler.process(node);
	    }
	}
	
	//广度优先
	public void breadthFirst(TreeNode treeNode , TreeNodeHandler handler) {

	    Deque<TreeNode> nodeDeque = new ArrayDeque<TreeNode>();

	    nodeDeque.add(treeNode);

	    while (!nodeDeque.isEmpty()) {

	        TreeNode node = nodeDeque.pop();

	        handler.process(node);

	        List<TreeNode> children = getChildren(node);

	        if (children != null && !children.isEmpty()) {

	            for (TreeNode child : children) {

	                nodeDeque.add(child);

	            }

	        }

	    }

	}

	private List<TreeNode> getChildren(TreeNode node) {
		return node.getChildren();
	}
	
}
