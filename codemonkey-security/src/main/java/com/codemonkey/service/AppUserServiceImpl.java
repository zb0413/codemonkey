package com.codemonkey.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.config.SecurityConfig;
import com.codemonkey.domain.AppUser;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.repository.AppUserRepository;
import com.codemonkey.utils.RegUtils;
import com.codemonkey.utils.SysUtils;

@Service
public class AppUserServiceImpl extends LogicalServiceImpl<AppUser , Long> implements AppUserService {
	
	@Autowired private SecurityConfig securityConfig;
	@Autowired private AppUserRepository appUserRepository;
	
	@Override
	protected Set<FieldValidation> validate(AppUser user) {
		Set<FieldValidation> set = super.validate(user);

		if (SysUtils.isEmpty(user.getUsername())) {
			set.add(new FormFieldValidation("username", "用户名不能为空"));
		} else if (!isUnique(user, "username", user.getUsername())) {
			set.add(new FormFieldValidation("username", "该登录名称已经存在，请重新设置"));
		} else if (!RegUtils.matches(RegUtils.NUM_LETTER_UNDERLINE_REG,
				user.getUsername())) {
			set.add(new FormFieldValidation("username", "用户名中只能包含数字、字母或下划线"));
		}

		if (SysUtils.isEmpty(user.getPassword())) {
			set.add(new FormFieldValidation("password", "密码不能为空"));
		}

		return set;
	}
	
	@Override
	public void save(AppUser t) {
		
		if(SysUtils.isNotEmpty(t.getRawPassword())){
			String password = securityConfig.getPasswordEncoder().encode(t.getRawPassword());
			t.setPassword(password);
		}
		
		super.save(t);
	}

	@Override
	public AppUser createEntity() {
		return new AppUser();
	}

	@Override
	public AppUserRepository getRepository() {
		return appUserRepository;
	}

}
