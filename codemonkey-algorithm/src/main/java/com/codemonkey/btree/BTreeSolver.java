package com.codemonkey.btree;

public class BTreeSolver {

	
	private void visited(BTreeNode root, BTreeNodeHandler handler) {
		handler.process(root);
	}
	
	/**
	 * 前序遍历
	 */
	public void preOrder(BTreeNode root , BTreeNodeHandler handler){
		if(root != null){
			visited(root , handler);
			preOrder(root.getLeft() , handler);
			preOrder(root.getRight() , handler);
		}
	}
	
	/**
	 * 中序遍历
	 * @param node
	 */
	
	public void inOrder(BTreeNode root , BTreeNodeHandler handler){
		if(root != null){
			inOrder(root.getLeft() , handler);
			visited(root , handler);
			inOrder(root.getRight() , handler);
		}
	}
	/**
	 * 后序遍历
	 * @param node
	 */
	
	public void postOrder(BTreeNode root , BTreeNodeHandler handler){
		if(root != null){
			postOrder(root.getLeft() , handler);
			postOrder(root.getRight() , handler);
			visited(root , handler);
		}
	}
}
