package com.codemonkey.dbMigration.script;

import org.springframework.core.io.Resource;

import com.codemonkey.dbMigration.migration.MigrationException;

public class MigrationFactory {
	
	public Migration create(String version, Resource resource) {
		final String fileName = resource.getFilename().toLowerCase();
		if (fileName.endsWith(MigrationType.SQL.getCode())) {
			return new SQLScriptMigration( version, resource);
		} 
		throw new MigrationException("Can't determine migration type for " + resource);
	}

}
