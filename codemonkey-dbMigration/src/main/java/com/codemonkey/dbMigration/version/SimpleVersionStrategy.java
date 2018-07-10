package com.codemonkey.dbMigration.version;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.collections.map.DefaultedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

import com.codemonkey.dbMigration.api.Configure;
import com.codemonkey.dbMigration.api.PropKeyHolder;
import com.codemonkey.dbMigration.jdbc.DBType;
import com.codemonkey.dbMigration.migration.MigrationException;

/**
 * A trivial VersionStrategy which tracks only the minimal information required
 * to note the current state of the database: the current version.
 */
public class SimpleVersionStrategy implements VersionStrategy {

	private String databaseSchema = Configure.getStringProperty(PropKeyHolder.DEFAULT_DATABASE_SCHEMA);
	private String versionTable = Configure.getStringProperty(PropKeyHolder.DEFAULT_VERSION_TABLE);
	private String versionColumn = Configure.getStringProperty(PropKeyHolder.DEFAULT_VERSION_COLUMN);
	private String appliedDateColumn = Configure.getStringProperty(PropKeyHolder.DEFAULT_APPLIED_DATE_COLUMN);
	private String durationColumn = Configure.getStringProperty(PropKeyHolder.DEFAULT_DURATION_COLUMN);

	private static final DefaultedMap ENABLE_VERSIONING_DLL;

	static {
		ENABLE_VERSIONING_DLL = new DefaultedMap(
				"create table %s (%s varchar(32) not null unique, %s timestamp not null, %s int not null)");
		ENABLE_VERSIONING_DLL
				.put(
						DBType.HSQL,
						"create table %s (%s varchar not null, %s datetime not null, %s int not null, constraint %2$s_unique unique (%2$s))");
		ENABLE_VERSIONING_DLL
				.put(
						DBType.SQL_SERVER,
						"create table %s (%s varchar(32) not null unique, %s datetime not null, %s int not null)");
	}

	public boolean isEnabled(DBType dbType, Connection connection) {
		try {
			
			String sql = "select count(*) from " + getVersionTableName();
			connection.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	public void enableVersioning(DBType dbType, Connection connection) {
		try {
			String ddl = "";
			if(StringUtils.isNotBlank(databaseSchema)){
				ddl += "CREATE SCHEMA " + databaseSchema  + ";";
			}
			
			ddl += String.format((String) ENABLE_VERSIONING_DLL.get(dbType),
					getVersionTableName(), versionColumn, appliedDateColumn,
					durationColumn);
			connection.createStatement().executeUpdate(ddl);
		} catch (SQLException e) {
			throw new MigrationException(
					"Could not create version-tracking table '" + getVersionTableName() + "'.", e);
		}
	}

	public Set<String> appliedMigrations(DBType dbType,
			Connection connection) {
		// Make sure migrations is enabled.
		if (!isEnabled(dbType, connection)) {
			return null;
		}

		Set<String> migrations = new HashSet<String>();

		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select " + versionColumn + " from " + getVersionTableName());
			while (rs.next()) {
				migrations.add(rs.getString(versionColumn));
			}
		} catch (SQLException e) {
			throw new MigrationException(e);
		}

		return migrations;
	}

	public void recordMigration(DBType dbType, Connection connection,
			String version, Date startTime, long duration) {
		try {
			PreparedStatement statement = connection
					.prepareStatement("insert into " + getVersionTableName()
							+ " values (?, ?, ?)");
			statement.setString(1, version);
			statement.setTimestamp(2, new Timestamp(startTime.getTime()));
			statement.setLong(3, duration);
			statement.execute();
		} catch (SQLException e) {
			throw new MigrationException(e);
		}
	}
	
	public String getVersion(Date date){
		Date date1 = date;
		if(date1 == null){
			date1 = new Date();
		}
		String version = FastDateFormat.getInstance(
				Configure.getStringProperty(PropKeyHolder.VERSION_PATTERN),
				TimeZone.getTimeZone(Configure.getStringProperty(PropKeyHolder.VERSION_TIME_ZONE))).format(date1);
		return version;
	}

	public void setVersionTable(String versionTable) {
		this.versionTable = versionTable;
	}

	public void setVersionColumn(String versionColumn) {
		this.versionColumn = versionColumn;
	}

	public void setAppliedDateColumn(String appliedDateColumn) {
		this.appliedDateColumn = appliedDateColumn;
	}

	public void setDurationColumn(String durationColumn) {
		this.durationColumn = durationColumn;
	}
	
	private String getVersionTableName(){
		String vTable = versionTable;
		if(StringUtils.isNotBlank(databaseSchema)){
			vTable = databaseSchema + "." + versionTable;
		}
		return vTable;
	}
}
