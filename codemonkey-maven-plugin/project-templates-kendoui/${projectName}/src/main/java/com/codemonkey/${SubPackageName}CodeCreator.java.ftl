package com.codemonkey;

//import com.codemonkey.domain.MeetingModule;
import com.codemonkey.generator.CodeGenerator;


public class ${SubPackageName}CodeCreator {

	public static void main(String[] args){
		CodeGenerator cg = new CodeGenerator();
		cg.setProjectName("${projectName}");
		cg.setTemplateDir("simple");
//		cg.run(MeetingModule.class);
	}
}
