package com.codemonkey.xqlBuilder;

import java.util.List;

public class XqlResult {

	private String xql;
	
	private List<Object> params;

	public String getXql() {
		return xql;
	}

	public void setXql(String xql) {
		this.xql = xql;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
}
