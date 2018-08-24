package com.codemonkey.btree;

import lombok.Data;

@Data
public class BTreeNode {
	
	private Object value;
	
	private BTreeNode left;
	
	private BTreeNode right;
	
}
