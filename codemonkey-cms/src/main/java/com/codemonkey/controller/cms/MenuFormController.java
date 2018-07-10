package com.codemonkey.controller.cms;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.cms.Menu;
import com.codemonkey.domain.cms.MenuSubList;
import com.codemonkey.service.cms.MenuService;
import com.codemonkey.service.cms.MenuSubListService;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.controller.AbsFormExtController;

@Controller
@RequestMapping("/ext/menu/**")
public class MenuFormController extends AbsFormExtController<Menu>{

	@Autowired private MenuService menuService;
	@Autowired private MenuSubListService menuSubListService;

	@Override
	protected MenuService service() {
		return menuService;
	}
	
	@Override
	public JSONObject jsonResult(Menu t) {
		// 返回主表
		JSONObject result = super.jsonResult(t);
		
		List<MenuSubList> detailList = menuSubListService.findAllBy("menu.id", t.getId());
		JSONArray array = new JSONArray();
		if (SysUtils.isNotEmpty(detailList)) {
			for (MenuSubList menuSubList : detailList) {
				array.put(menuSubList.listJson());
			}
		}
		// 返回明细
		result.put("detailGrid", array);
				
		return result;
	}
}
