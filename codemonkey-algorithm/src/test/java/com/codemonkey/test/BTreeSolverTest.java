package com.codemonkey.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codemonkey.btree.BTreeNode;
import com.codemonkey.btree.BTreeSolver;

public class BTreeSolverTest{

	@Test
	public void test(){
		
		BTreeNode a = new BTreeNode();
		a.setValue("A");
		
		BTreeNode b = new BTreeNode();
		b.setValue("B");
		
		BTreeNode c = new BTreeNode();
		c.setValue("C");
		
		BTreeNode d = new BTreeNode();
		d.setValue("D");
		
		BTreeNode e = new BTreeNode();
		e.setValue("E");
		
		BTreeNode f = new BTreeNode();
		f.setValue("F");
		
		a.setLeft(b);
		a.setRight(c);
		
		b.setLeft(d);
		b.setRight(e);
		
		c.setLeft(f);
		
		BTreeSolver solver = new BTreeSolver();
		
		TestBTreeNodeHandler handler1 = new TestBTreeNodeHandler();
		solver.preOrder(a , handler1);
		assertEquals("ABDECF" , handler1.getResult());
		
		TestBTreeNodeHandler handler2 = new TestBTreeNodeHandler();
		solver.inOrder(a , handler2);
		assertEquals("DBEAFC" , handler2.getResult());
		
		TestBTreeNodeHandler handler3 = new TestBTreeNodeHandler();
		solver.postOrder(a , handler3);
		assertEquals("DEBFCA" , handler3.getResult());
	}
}
