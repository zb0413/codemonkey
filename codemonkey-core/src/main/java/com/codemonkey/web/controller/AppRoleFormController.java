package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.FunctionNode;
import com.codemonkey.domain.TreeEntity;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.FunctionNodeService;
import com.codemonkey.tree.TreeUtils;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping("/ext/appRole/**")
public class AppRoleFormController extends AbsFormExtController<AppRole> {

	protected static final String getParentField = "parent.id";

	@Autowired
	private AppRoleService appRoleService;

	@Autowired
	private FunctionNodeService functionNodeService;

	@Override
	protected AppRoleService service() {
		return appRoleService;
	}

	@Override
	public JSONObject jsonResult(AppRole t) {
		JSONObject jo = super.jsonResult(t);

		List<FunctionNode> all = functionNodeService.findAll();
		List<TreeEntity> allList = new ArrayList<TreeEntity>();
		if (SysUtils.isNotEmpty(all)) {
			allList.addAll(all);
			jo.put("allFunctionNodes", SysUtils.toJsonArry(allList));
			jo.put("functionNodes", TreeUtils.buildTree(allList));
		}

		List<AppRole> roleList = new ArrayList<AppRole>();
		JSONArray ja = new JSONArray();
		if (t.getId() != null) {
			roleList.add(t);
			List<FunctionNode> availableNodes = functionNodeService
					.getFunctionNodesByRoles(roleList);

			if (SysUtils.isNotEmpty(availableNodes)) {
				for (FunctionNode f : availableNodes) {
					JSONObject json = f.listJson();
					json.put("text", json.getString("name"));
					ja.put(json);
				}
			}
		}
		jo.put("checkedFunctionNodes", ja);
		return jo;
	}
}
