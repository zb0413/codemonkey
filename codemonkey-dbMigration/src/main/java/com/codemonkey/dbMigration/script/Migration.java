package com.codemonkey.dbMigration.script;

import java.sql.Connection;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;

import com.codemonkey.dbMigration.jdbc.DBType;

public interface Migration extends Comparable<Migration> {
	String getVersion();

	String getFilename();
	
	void setVersion(String version);
	
	void setResource(Resource resource);

	void migrate(DBType dbType, Connection connection);

	class MigrationVersionPredicate implements Predicate {
		private final String version;

		public MigrationVersionPredicate(String version) {
			this.version = version;
		}

		public boolean evaluate(Object object) {
			return StringUtils.equalsIgnoreCase(((Migration) object)
					.getVersion(), version);
		}
	}
}
