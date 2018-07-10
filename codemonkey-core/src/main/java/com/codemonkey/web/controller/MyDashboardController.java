package com.codemonkey.web.controller;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.UserDashboard;
import com.codemonkey.service.UserDashboardService;
import com.codemonkey.utils.HqlHelper;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping("/ext/myDashboard/**")
public class MyDashboardController extends AbsBatchedController<UserDashboard>{

	@Autowired private UserDashboardService userDashboardService;
	
	@Override
	protected UserDashboardService service() {
		return userDashboardService;
	}
	
	@Override
	protected String handleRead(String id, String query, JSONArray sort, JSONObject queryInfo) {
		
		queryInfo.put("appUser.id", SysUtils.getCurrentUser().getId());
		
		JSONObject sort1 = new JSONObject();
		sort1.put(HqlHelper.PROPERTY , "colIndex");
		sort1.put(HqlHelper.DIRECTION , HqlHelper.ASC);
		sort.put(sort1);
		
		JSONObject sort2 = new JSONObject();
		sort2.put(HqlHelper.PROPERTY , "sortIndex");
		sort2.put(HqlHelper.DIRECTION , HqlHelper.ASC);
		sort.put(sort2);
		
		return super.handleRead(id, query, sort, queryInfo);
	}
	
	@Override
	public String handleUpdate(Map<String,String[]> body) {
		
		JSONObject params = parseJson(body);
		
		params.put("appUser", SysUtils.getCurrentUser().getId());
		
		UserDashboard t = service().doSave(params , getCcService());
		
		return result(service().get(t.getId()));
	}

}
