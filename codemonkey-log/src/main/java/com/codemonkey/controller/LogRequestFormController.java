package com.codemonkey.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.LogReqParam;
import com.codemonkey.domain.LogRequest;
import com.codemonkey.service.log.LogReqParamService;
import com.codemonkey.service.log.LogRequestService;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.controller.AbsFormExtController;

@Controller
@RequestMapping("/ext/logRequest/**")
public class LogRequestFormController extends AbsFormExtController<LogRequest> {

	@Autowired
	private LogRequestService logRequestService;
	@Autowired
	private LogReqParamService logReqParamService;

	@Override
	protected LogRequestService service() {
		return logRequestService;
	}

	@Override
	public JSONObject jsonResult(LogRequest t) {

		JSONObject result = super.jsonResult(t);

		// 获取操作方法的参数信息
		JSONArray logReqParamJa = new JSONArray();
		if (t.getId() != null) {
			List<LogReqParam> logReqParamList = logReqParamService.findAllBy(
					"LogRequest.id", t.getId());

			if (SysUtils.isNotEmpty(logReqParamList)) {
				for (LogReqParam logReqParam : logReqParamList) {
					logReqParamJa.put(logReqParam.detailJson());
				}
			}
		}
		result.put("lines", logReqParamJa);
		return result;
	}
}
