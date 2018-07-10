package com.codemonkey.init;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.dbMigration.api.Configure;
import com.codemonkey.dbMigration.jdbc.DBType;
import com.codemonkey.dbMigration.migration.DriverManagerMigrationManager;
import com.codemonkey.dbMigration.migration.MigrationManager;

@Component
@InitOrder(4)
public class InitDbMigrationServiceImpl implements InitDataBean{
	
	@Autowired
	@Resource(name="dataSource")
	private DataSource datasource;

	@Override
	public void doInit(){
		Configure.configure();
		MigrationManager migrationManager = new DriverManagerMigrationManager(datasource, DBType.MYSQL);
		migrationManager.migrate();
	}
}
