package com.codemonkey.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.FunctionNode;

@RepositoryRestResource(path = "functionNode")
public interface FunctionNodeRepository extends PagingAndSortingRepository<FunctionNode, Long> {

}
