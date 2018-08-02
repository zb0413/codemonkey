package com.codemonkey.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.AppPermission;

@RepositoryRestResource(path = "appPermission")
public interface AppPermissionRepository extends PagingAndSortingRepository<AppPermission, Long> {

}
