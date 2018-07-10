package com.codemonkey.dbMigration.script;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.springframework.core.io.Resource;

import com.codemonkey.dbMigration.jdbc.DBType;
import com.codemonkey.dbMigration.jdbc.ScriptRunner;
import com.codemonkey.dbMigration.migration.MigrationException;

public class SQLScriptMigration extends AbstractMigration {

	public SQLScriptMigration(){
		super();
	}
	
	public SQLScriptMigration(String version, Resource script) {
		super(version, script.getFilename());
		this.setResource(script);
	}

	public void migrate(DBType dbType, Connection connection) {
		InputStream inputStream = null;
		try {
			inputStream = getResource().getInputStream();
			ScriptRunner scriptRunner = new ScriptRunner();
			scriptRunner.execute(connection, new InputStreamReader(inputStream,
					"UTF-8"));
			Validate.isTrue(!connection.isClosed(),
					"JDBC Connection should not be closed.");
		} catch (IOException e) {
			throw new MigrationException(
					"Error while reading script input stream.", e);
		} catch (SQLException e) {
			getLogger().error(e.getMessage());
			throw new MigrationException(
					"Error while executing migration script.", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
}
