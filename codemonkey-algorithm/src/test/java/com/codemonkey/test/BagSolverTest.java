package com.codemonkey.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codemonkey.bag.BagItem;
import com.codemonkey.bag.BagSolution;
import com.codemonkey.bag.BagSolver;

public class BagSolverTest {

	@Test
	public void test() {
    	 // 可放入包中的备选物品
        BagItem[] sourceItems = { new BagItem("4号球", 4, 5) ,  new BagItem("5号球", 5, 6) , new BagItem("6号球", 6, 7) };
        int bagSize = 10; // 包的空间
    	BagSolver bag = new BagSolver(sourceItems , bagSize);
    	bag.solve();
        BagSolution okBagResult = bag.getOkBags()[bag.getItemCount()][bag.getBagSize()];
        assertEquals( 2 , okBagResult.getSize());
        assertEquals( 10d , okBagResult.getValue() , 10e-6);
	}
}
