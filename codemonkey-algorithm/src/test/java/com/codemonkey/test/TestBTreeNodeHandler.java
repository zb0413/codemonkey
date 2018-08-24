package com.codemonkey.test;

import com.codemonkey.btree.BTreeNode;
import com.codemonkey.btree.BTreeNodeHandler;

import lombok.Data;

@Data
public class TestBTreeNodeHandler implements BTreeNodeHandler{

	private String result = "";
	
	@Override
	public void process(BTreeNode node) {
		result += node.getValue().toString();
	}

}
