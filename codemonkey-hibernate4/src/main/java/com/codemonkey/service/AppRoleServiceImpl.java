package com.codemonkey.service;

import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.CmpPermission;
import com.codemonkey.domain.FunctionNode;
import com.codemonkey.domain.UrlPermission;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.utils.JsonArrayConverter;
import com.codemonkey.utils.SysUtils;

@Primary
@Service
public class AppRoleServiceImpl extends LogicalServiceImpl<AppRole> implements
		AppRoleService {

	@Autowired
	private CmpPermissionService cmpPermissionService;

	@Override
	public AppRole buildEntity(JSONObject params,
			FormattingConversionServiceFactoryBean ccService) {
		AppRole appRole = super.buildEntity(params, ccService);

		appRole.clearUrlPermissions();
		JsonArrayConverter<UrlPermission> urlPermissionConverter = new JsonArrayConverter<UrlPermission>();
		List<UrlPermission> permissions = urlPermissionConverter.convert(
				params, "urlPermissions", UrlPermission.class, ccService);
		for (UrlPermission urlPermission : permissions) {
			appRole.addUrlPermission(urlPermission);
		}

		appRole.clearCmpPermissions();
		JsonArrayConverter<CmpPermission> cmpPermissionConverter = new JsonArrayConverter<CmpPermission>();
		List<CmpPermission> cmpPermissions = cmpPermissionConverter.convert(
				params, "cmpPermissions", CmpPermission.class, ccService);
		for (CmpPermission cmpPermission : cmpPermissions) {
			cmpPermissionService.save(cmpPermission);
			appRole.addCmpPermission(cmpPermission);
		}

		appRole.clearFunctionNodes();
		JsonArrayConverter<FunctionNode> functionNodeConverter = new JsonArrayConverter<FunctionNode>();
		List<FunctionNode> functionNodes = functionNodeConverter.convert(
				params, "functionNodes", FunctionNode.class, ccService);
		for (FunctionNode functionNode : functionNodes) {
			appRole.addFunctionNode(functionNode);
		}

		return appRole;
	}

	@Override
	public AppRole createEntity() {
		return new AppRole();
	}

	@Override
	public boolean hasFunctionNode(Long roleId, Long functionNodeId) {

		String hql = " select FN from AppRole R inner join R.functionNodes FN where R.id = ? and FN.id = ?";

		Object functionNode = this.uniqueResult(hql, roleId, functionNodeId);

		if (functionNode != null) {
			return true;
		}

		return false;
	}

	@Override
	protected Set<FieldValidation> validate(AppRole appRole) {
		Set<FieldValidation> set = super.validate(appRole);
		if (SysUtils.isEmpty(appRole.getName())) {
			set.add(new FormFieldValidation("name", "角色名称不能为空！"));
		}

		if (!isUnique(appRole, "name", appRole.getName())) {
			set.add(new FormFieldValidation("name", "该名称已经存在，名称不能重复！"));
		}

		return set;
	}
}
