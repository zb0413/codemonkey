package com.codemonkey.service;

import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

import com.codemonkey.SysProp;
import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.utils.JsonArrayConverter;
import com.codemonkey.utils.RegUtils;
import com.codemonkey.utils.SysUtils;

@Primary
@Service
public class AppUserServiceImpl extends LogicalServiceImpl<AppUser> implements
		AppUserService {

	@Autowired private SysProp sysProp;
	
	@Override
	public AppUser createEntity() {
		return new AppUser();
	}

	@Autowired
	private UrlPermissionService urlPermissionService;

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
	public AppUser buildEntity(JSONObject params,
			FormattingConversionServiceFactoryBean ccService) {

		AppUser appUser = super.buildEntity(params, ccService);

		String password = params.optString("password");

		if (SysUtils.isNotEmpty(password)) {
			
			String salt = AppUser.generateSalt();
			String hashedPasswordBase64 = SysUtils.encrypt(sysProp.getPasswordEncrypt() , password , salt);
			appUser.setPassword(hashedPasswordBase64);
			appUser.setSalt(salt);
		}

		appUser.clearAppRoles();
		JsonArrayConverter<AppRole> appRolesConverter = new JsonArrayConverter<AppRole>();
		List<AppRole> roles = appRolesConverter.convert(params, "roles",
				AppRole.class, ccService);
		for (AppRole role : roles) {
			appUser.addAppRole(role);
		}

		return appUser;
	}

//	@Override
//	public AppUser doChangePassword(JSONObject body,
//			FormattingConversionServiceFactoryBean ccService) {
//		Set<FieldValidation> set = new HashSet<FieldValidation>();
//		if (body.has("password")
//				&& StringUtils.isNotBlank(body.getString("password"))
//				&& body.has("password_ack")) {
//			String password = body.getString("password");
//			String passwordAck = body.getString("password_ack");
//
//			if (!password.equals(passwordAck)) {
//				set.add(new FormFieldValidation("password", "密码不同"));
//			}
//
//		}
//
//		if (CollectionUtils.isNotEmpty(set)) {
//			throw new ValidationError(set);
//		}
//
//		AppUser user = super.buildEntity(body, ccService);
//		save(user);
//		return user;
//	}

	@Override
	public boolean hasRole(Long userid, Long roleid) {

		String hql = " select FN from AppUser R inner join R.roles FN where R.id = ? and FN.id = ?";

		Object approle = this.uniqueResult(hql, userid, roleid);

		if (approle != null) {
			return true;
		}

		return false;
	}
}
