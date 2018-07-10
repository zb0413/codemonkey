package com.codemonkey.dbMigration.api;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.dbMigration.jdbc.DBType;
import com.codemonkey.dbMigration.migration.DriverManagerMigrationManager;
import com.codemonkey.dbMigration.migration.MigrationManager;

@Service
public class DatabaseManager {

	@Autowired private List<DataSource> dataSources;
	
	public void run(){
		
		for(DataSource dataSource : dataSources){
			MigrationManager migrationManager = new DriverManagerMigrationManager(dataSource , DBType.POSTGRESQL);
			migrationManager.migrate();
		}
	}
}
