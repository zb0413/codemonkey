package com.codemonkey.dbMigration.resovler;

import java.util.Set;

import com.codemonkey.dbMigration.jdbc.DBType;
import com.codemonkey.dbMigration.script.Migration;

/**
 * Defines a strategy for finding all of the migrations which could potentially
 * be run against a database.
 */
public interface MigrationResolver {
	/**
	 * Find all of the available migrations.
	 * 
	 * @return a set of all available migrations, empty if no migrations are
	 *         available
	 */
	Set<Migration> resolve();

	/**
	 * Find all of the available migrations.
	 * 
	 * @param dbType
	 *            database type
	 * @return a set of all available migrations, empty if no migrations are
	 *         available
	 */
	Set<Migration> resolve(DBType dbType);
}
