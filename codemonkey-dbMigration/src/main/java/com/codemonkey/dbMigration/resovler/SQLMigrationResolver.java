package com.codemonkey.dbMigration.resovler;

import com.codemonkey.dbMigration.api.Configure;
import com.codemonkey.dbMigration.api.PropKeyHolder;
import com.codemonkey.dbMigration.script.MigrationType;
import com.codemonkey.dbMigration.version.SimpleVersionExtractor;
import com.codemonkey.dbMigration.version.VersionExtractor;

/**
 * A MigrationResolver which leverages Spring's robust Resource loading
 * mechanism, supporting 'file:', 'classpath:', and standard url format
 * resources.
 * <p/>
 * Migration Location Examples:
 * <ul>
 * <li>classpath:/database/</li>
 * </ul>
 * All of the resources found in the migrations location which do not start with
 * a '.' will be considered migrations.
 * <p/>
 * Configured out of the box with a SimpleVersionExtractor and the default
 * resource pattern CLASSPATH_MIGRATIONS_SQL.
 * 
 * @see Resource
 * @see PathMatchingResourcePatternResolver
 * @see VersionExtractor
 * @see MigrationFactory
 */
public class SQLMigrationResolver extends AbstractMigrationResolver implements MigrationResolver {

	public SQLMigrationResolver() {
		this(Configure.getStringProperty(PropKeyHolder.SQL_MIGRATION_PATH));
	}

	public SQLMigrationResolver(String migrationsLocation) {
		this(migrationsLocation, new SimpleVersionExtractor());
	}

	public SQLMigrationResolver(String migrationsLocation,
			VersionExtractor versionExtractor) {
		this.setMigrationsLocation(migrationsLocation) ;
		this.setVersionExtractor(versionExtractor);
	}

	@Override
	protected String getExtendsion() {
		return MigrationType.SQL.getCode();
	}

}
