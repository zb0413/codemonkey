package com.codemonkey;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public final class FreeMakerGenerator {
	
	private FreeMakerGenerator(){}

	public static void export(String templatePath, Map<String, Object> binding , String destinationPath) {
		try{
			File templateFile = new File(templatePath);
			
			File destinationFile = new File(destinationPath);
			File destinationParent = destinationFile.getParentFile();
			if(!destinationParent.exists()){
				FileUtils.forceMkdir(destinationParent);
			}
			
			if(templateFile.isDirectory()){
				FileUtils.forceMkdir(destinationParent);
			}else if(templateFile.isFile()){
				if(templatePath.endsWith(Constant.TEMPLATE_SUBFIX)){
					export(templateFile , binding , destinationFile);
				}else{
					FileUtils.copyFile(templateFile, destinationFile);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void export(File templateFile, Map<String, Object> binding , File destinationFile) {
		 /* ------------------------------------------------------------------- */    
        /* You should do this ONLY ONCE in the whole application life-cycle:   */    
		try{
			 /* Create and adjust the configuration */
	        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
	        cfg.setDefaultEncoding("UTF-8");
	        //template dir
	        cfg.setDirectoryForTemplateLoading(templateFile.getParentFile());
	        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_0));

	        /* ------------------------------------------------------------------- */    
	        /* You usually do these for many times in the application life-cycle:  */    
	                
	        /* Get or create a template */
	        Template temp = cfg.getTemplate(templateFile.getName());
//	        temp.setEncoding("UTF-8");
	        
	        /* Merge data-model with template */
	        Writer out = new OutputStreamWriter(new FileOutputStream(destinationFile) , "UTF-8");
	        temp.process(binding, out);
	        out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
       
	}
}
