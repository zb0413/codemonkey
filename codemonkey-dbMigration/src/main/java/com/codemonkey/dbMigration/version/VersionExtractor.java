package com.codemonkey.dbMigration.version;

import org.springframework.core.io.Resource;

public interface VersionExtractor {
	String extractVersion(Resource resource);
}
