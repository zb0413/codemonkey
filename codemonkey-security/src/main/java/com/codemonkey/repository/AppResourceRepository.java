package com.codemonkey.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.AppResource;

@RepositoryRestResource(path = "appResource")
public interface AppResourceRepository extends PagingAndSortingRepository<AppResource, Long> {

}
