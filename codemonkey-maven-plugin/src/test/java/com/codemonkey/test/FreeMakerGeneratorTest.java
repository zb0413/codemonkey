package com.codemonkey.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

public class FreeMakerGeneratorTest extends TestCase{

	File tempFile;
	Map<String , Object> binding;
	
	protected void setUp() throws Exception{
		binding = new HashMap<String , Object>();
		binding.put("entityName", "foo");
	}
	
	@Test
	public void test(){
//		File tempFile = new File("code-templates/helloWorld.htm.ftl");
//		File targetFile = new File("code-templates/target/helloWorld.htm");
//		FreeMakerGenerator.export(tempFile, binding, targetFile);
//		File f = new File("code-templates/target/helloWorld.htm");
//		assertTrue(f.exists());
	}
}
