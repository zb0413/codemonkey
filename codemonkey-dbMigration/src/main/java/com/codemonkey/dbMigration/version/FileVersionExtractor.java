package com.codemonkey.dbMigration.version;

import java.io.File;
import java.util.Date;

import org.springframework.core.io.Resource;

public class FileVersionExtractor extends SimpleVersionExtractor {
	
	private VersionStrategy versionStrategy = new SimpleVersionStrategy(); 
	
	@Override
	public String extractVersion(Resource resource){
		try{
			File file = resource.getFile();
			if(!file.exists()){
				throw new RuntimeException(file.getPath() + " does not exist !");
			}
			if(!file.isFile()){
				throw new RuntimeException(file.getPath() + " is not a file !");
			}
			Date lastModifiedDate = new Date(file.lastModified());
			String verion = versionStrategy.getVersion(lastModifiedDate);
			return super.extractVersion(verion);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
