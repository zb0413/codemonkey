package com.codemonkey;

import com.codemonkey.generator.CodeGenerator;


public class CmsCodeCreator {

	public static void main(String[] args){
		CodeGenerator cg = new CodeGenerator();
		cg.setSubPackage("cms");
		cg.setProjectName("codemonkey-cms");
		//cg.run(Menu.class);
	}
}
