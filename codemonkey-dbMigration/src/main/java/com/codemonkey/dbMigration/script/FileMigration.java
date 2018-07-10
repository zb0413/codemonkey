package com.codemonkey.dbMigration.script;

import org.springframework.core.io.Resource;

public abstract class FileMigration extends AbstractMigration {
	
	public FileMigration(){
		super();
	}
	
	public FileMigration(String version, Resource resource) {
		super(version, resource.getFilename());
		this.setResource(resource);
	}
}
