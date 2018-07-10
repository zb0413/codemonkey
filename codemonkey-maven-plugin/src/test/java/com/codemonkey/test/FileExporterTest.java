package com.codemonkey.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.codemonkey.generator.FileExporter;

public class FileExporterTest extends TestCase{

	FileExporter fileExporter;
	Map<String , Object> binding;
	
	protected void setUp() throws Exception{
		String tempRoot = "code-templates";
		String targetRoot = "code-templates/target";
		fileExporter = new FileExporter(tempRoot , targetRoot );
		binding = new HashMap<String , Object>();
		binding.put("entityName", "foo");
		binding.put("EntityName", "Foo");
		binding.put("entityname", "Foo");
	}
	
	@Test
	public void testDestinationFile(){
//		String destFilePath = fileExporter.getDestinationFile("code-templates/helloWorld.htm.ftl" , binding);
//		assertEquals("code-templates/target/helloWorld.htm", destFilePath);
//		
////		destFilePath = fileExporter.getDestinationFile("code-templates/entity/helloWorld.htm.ftl", binding);
////		assertEquals("code-templates/target/entity/helloWorld.htm", destFilePath);
//		
//		destFilePath = fileExporter.getDestinationFile("code-templates/entity/${EntityName}.java.ftl", binding);
//		assertEquals("code-templates/target/entity/Foo.java", destFilePath);
//		
//		destFilePath = fileExporter.getDestinationFile("code-templates/${EntityName}/Edit.js.ftl", binding);
//		assertEquals("code-templates/target/Foo/Edit.js", destFilePath);
	}
	
	@Test
	public void test(){
//		fileExporter.run(binding);
//		File f = new File("code-templates/target/helloWorld.htm");
//		assertTrue(f.exists());
//		f = new File("code-templates/target/entity/Foo.java");
//		assertTrue(f.exists());
	}
}
