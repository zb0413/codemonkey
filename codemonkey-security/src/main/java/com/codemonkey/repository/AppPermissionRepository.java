package com.codemonkey.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.support.CodeMonkeyRepository;

@RepositoryRestResource(path = "appPermission")
public interface AppPermissionRepository extends CodeMonkeyRepository<AppPermission, Long> {

}
