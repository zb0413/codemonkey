package com.codemonkey.dbMigration.script;

import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;


public enum MigrationType {
	SQL("sql"),
	GROOVY("groovy");
	
	private String code;
	
	private static final Map<String, MigrationType> LOOKUP = new TreeMap<String, MigrationType>();

	static {
		for (MigrationType elot : EnumSet.allOf(MigrationType.class)) {
			LOOKUP.put(elot.getCode(), elot);
		}
	}
	
	public String getCode() {
		return this.code;
	}
	
	public static MigrationType fromCode(String code) {
		return LOOKUP.get(code);
	}
	
	MigrationType(String code) {
		this.code = code;
	}
}
