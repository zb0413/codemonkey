package com.codemonkey.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.FunctionNode;
import com.codemonkey.support.CodeMonkeyRepository;

@RepositoryRestResource(path = "functionNode")
public interface FunctionNodeRepository extends CodeMonkeyRepository<FunctionNode, Long> {

}
