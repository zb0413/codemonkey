package com.codemonkey.test.drools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codemonkey.drools.RuleRunner;

public class MessageTest extends  AbsDroolsServiceTest {

	@Test
	public void test(){
		Message message = new Message();
		message.setMessage("Hello World");
		message.setStatus(Message.HELLO);
		RuleRunner.runRules("rules/Sample.drl", message);
		assertEquals(Message.GOODBYE , message.getStatus());
	}
}
