package com.codemonkey.dbMigration.migration;

import javax.sql.DataSource;

import com.codemonkey.dbMigration.jdbc.DBType;

public class DriverManagerMigrationManager extends DataSourceMigrationManager {
	public DriverManagerMigrationManager(DataSource dataSource) {
		super(dataSource);
	}

	public DriverManagerMigrationManager(DataSource dataSource , DBType dbType) {
		super(dataSource , dbType);
	}
}
