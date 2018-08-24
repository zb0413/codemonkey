package com.codemonkey.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codemonkey.tree.TreeNode;
import com.codemonkey.tree.TreeSolver;

public class TreeSolverTest{

	@Test
	public void test(){
		
		TreeNode a = new TreeNode();
		a.setValue("A");
		
		TreeNode b = new TreeNode();
		b.setValue("B");
		
		TreeNode c = new TreeNode();
		c.setValue("C");
		
		TreeNode d = new TreeNode();
		d.setValue("D");
		
		TreeNode e = new TreeNode();
		e.setValue("E");
		
		TreeNode f = new TreeNode();
		f.setValue("F");
		
		a.getChildren().add(b);
		a.getChildren().add(c);
		
		b.getChildren().add(d);
		b.getChildren().add(e);
		
		c.getChildren().add(f);
		
		TreeSolver solver = new TreeSolver();
		
		TestTreeNodeHandler handler1 = new TestTreeNodeHandler();
		solver.breadthFirst(a , handler1);
		
		assertEquals("ABCDEF" , handler1.getResult());
		
		TestTreeNodeHandler handler2 = new TestTreeNodeHandler();
		solver.depthFirst(a , handler2);
		
		assertEquals("ACFBED" , handler2.getResult());
	}
}
