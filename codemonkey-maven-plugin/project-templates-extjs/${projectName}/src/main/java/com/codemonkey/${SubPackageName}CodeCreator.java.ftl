package com.codemonkey;

import com.codemonkey.generator.CodeGenerator;


public class ${SubPackageName}CodeCreator {

	public static void main(String[] args){
		CodeGenerator cg = new CodeGenerator();
		cg.setSubPackage("${subPackageName}");
		cg.setProjectName("${projectName}");
		//cg.run(StudentBak.class);
	}
}
