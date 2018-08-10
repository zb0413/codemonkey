package com.codemonkey.repository.jpa;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootConfiguration
@EnableJpaRepositories(
	repositoryBaseClass = JpaCodeMonkeyRepositoryImpl.class,
	basePackages = "*.*.repository"
)
@EnableAutoConfiguration
class JpaCustomRepositoryConfig {
	
}
