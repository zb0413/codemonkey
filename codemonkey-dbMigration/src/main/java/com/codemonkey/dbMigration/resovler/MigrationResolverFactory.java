package com.codemonkey.dbMigration.resovler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.codemonkey.dbMigration.script.MigrationType;

public class MigrationResolverFactory {

	private static final Map<String, MigrationResolver> LOOKUP = new TreeMap<String, MigrationResolver>();
	
	static {
		LOOKUP.put(MigrationType.SQL.getCode(), new SQLMigrationResolver());
	}
	
	public List<MigrationResolver> getAll(){
		return new ArrayList<MigrationResolver>(LOOKUP.values());
	}
}
