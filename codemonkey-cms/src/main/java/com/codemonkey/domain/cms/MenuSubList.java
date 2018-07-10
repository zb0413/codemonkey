package com.codemonkey.domain.cms;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.domain.AbsEE;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("栏目列表")
public class MenuSubList extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@Label("所属栏目")
	private Menu menu;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("menu", OgnlUtils.stringValue("menu.id", this));
		jo.put("menu_name", OgnlUtils.stringValue("menu.name", this));
		return jo;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
 
	
}
