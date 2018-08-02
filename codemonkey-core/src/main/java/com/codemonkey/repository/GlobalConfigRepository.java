package com.codemonkey.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.GlobalConfig;
import com.codemonkey.support.CodeMonkeyRepository;

@RepositoryRestResource(path = "globalConfig")
public interface GlobalConfigRepository extends CodeMonkeyRepository<GlobalConfig, Long> {

}
