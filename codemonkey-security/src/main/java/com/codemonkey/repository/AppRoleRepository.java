package com.codemonkey.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.AppRole;
import com.codemonkey.support.CodeMonkeyRepository;

@RepositoryRestResource(path = "appRole")
public interface AppRoleRepository extends CodeMonkeyRepository<AppRole, Long> {

	AppRole findByCode(String adminRole);

}
