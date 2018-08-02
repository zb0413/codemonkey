package com.codemonkey.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.codemonkey.domain.AppUser;
import com.codemonkey.support.CodeMonkeyRepository;

@RepositoryRestResource(path = "appUser")
public interface AppUserRepository extends CodeMonkeyRepository<AppUser, Long> {

	AppUser findByUsername(String userName);

}
