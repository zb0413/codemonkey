package com.codemonkey.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.AppResource;
import com.codemonkey.support.CodeMonkeyRepository;

@RepositoryRestResource(path = "appResource")
public interface AppResourceRepository extends CodeMonkeyRepository<AppResource, Long> {

}
