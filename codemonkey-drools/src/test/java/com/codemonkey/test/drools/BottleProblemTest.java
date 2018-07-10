package com.codemonkey.test.drools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codemonkey.drools.RuleRunner;

public class BottleProblemTest extends  AbsDroolsServiceTest{

	@Test
	public void test(){
		BottleProblem bp = new BottleProblem();
		RuleRunner.runRules("rules/Bottle.drl", bp);
		assertEquals(99 , bp.getTotalDrinked());
	}
}
