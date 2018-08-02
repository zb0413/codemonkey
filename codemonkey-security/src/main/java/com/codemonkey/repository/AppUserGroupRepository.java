package com.codemonkey.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.AppUserGroup;

@RepositoryRestResource(path = "appUserGroup")
public interface AppUserGroupRepository extends PagingAndSortingRepository<AppUserGroup, Long> {

}
