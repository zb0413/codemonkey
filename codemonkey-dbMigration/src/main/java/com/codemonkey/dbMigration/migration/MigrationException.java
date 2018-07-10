package com.codemonkey.dbMigration.migration;

// MigrationFailedException
// DuplicateMigrationException
public class MigrationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5897208334193648647L;

	public MigrationException() {
	}

	public MigrationException(String message) {
		super(message);
	}

	public MigrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public MigrationException(Throwable cause) {
		super(cause);
	}
}
