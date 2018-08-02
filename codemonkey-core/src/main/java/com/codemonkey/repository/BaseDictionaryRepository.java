package com.codemonkey.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.BaseDictionary;
import com.codemonkey.support.CodeMonkeyRepository;

@RepositoryRestResource(path = "baseDictionary")
public interface BaseDictionaryRepository extends CodeMonkeyRepository<BaseDictionary, Long> {

}
