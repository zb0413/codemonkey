package com.codemonkey.repository;

import io.swagger.annotations.Api;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.GlobalConfig;
import com.codemonkey.support.CodeMonkeyRepository;

@Api(tags = "GlobalConfigRepository")
@RepositoryRestResource(path = "globalConfig")
public interface GlobalConfigRepository extends CodeMonkeyRepository<GlobalConfig, Long> {

}
