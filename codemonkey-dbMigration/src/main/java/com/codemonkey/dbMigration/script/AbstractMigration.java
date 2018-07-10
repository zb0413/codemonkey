package com.codemonkey.dbMigration.script;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;


public abstract class AbstractMigration implements Migration {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String version;
	private String filename;
	private Resource resource;

	protected AbstractMigration(){
		
	}
	
	protected AbstractMigration(String version, String filename) {
		Validate.notEmpty(version);
		Validate.notEmpty(filename);
		this.setVersion(version);
		this.filename = filename;
	}

	public String getVersion() {
		return version;
	}

	public String getFilename() {
		return filename;
	}

	public int compareTo(Migration o) {
		return getVersion().compareTo(o.getVersion());
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Resource getResource() {
		return resource;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Logger getLogger() {
		return logger;
	}
}
