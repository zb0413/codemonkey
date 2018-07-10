package com.codemonkey.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.GanttDependencies;
import com.codemonkey.domain.GanttTask;
import com.codemonkey.web.controller.AbsController;

@Controller
@RequestMapping("/kendoui/**")
public class KendouiController extends AbsController{

	@RequestMapping("{page}")
	public String ajaxrequest(@PathVariable String page) {
		return page;
	}

	@RequestMapping("/form")
	public String form() {
		return "form";
	}
	
	@RequestMapping("/barcode")
	public String barcode() {
		return "barcode";
	}
	
	@RequestMapping("/text")
	public String text() {
		return "text";
	}
	
	@RequestMapping("/dropdown")
	public String dropdown() {
		return "dropdown";
	}
	
	@RequestMapping("/multiselect")
	public String multiselect() {
		return "multiselect";
	}
	
	@RequestMapping("/layout")
	public String layout() {
		return "layout";
	}
	
	@RequestMapping("/auto")
	public String auto() {
		return "auto";
	}
	
	@RequestMapping("/navigation")
	public String navigation() {
		return "navigation";
	}
	
	@RequestMapping("/button")
	public String button() {
		return "button";
	}
	
	@RequestMapping("/menu")
	public String menu() {
		return "menu";
	}
	
	@RequestMapping("/panelBar")
	public String panelBar() {
		return "panelBar";
	}
	
	@RequestMapping("/treeview")
	public String treeview() {
		return "treeview";
	}
	
	
	@RequestMapping("/tabpage")
	public String tabpage() {
		return "tabpage";
	}
	
	@RequestMapping("/toolbar")
	public String toolbar() {
		return "toolbar";
	}
	
	
	@RequestMapping("/columnchart")
	public String columnchart() {
		return "columnchart";
	}
	
	@RequestMapping("/linechart")
	public String linechart() {
		return "linechart";
	}
	
	@RequestMapping("/barchart")
	public String barchart() {
		return "barchart";
	}
	
	@RequestMapping("/piechart")
	public String piechart() {
		return "piechart";
	}
	
	@RequestMapping("/autocomplete")
	@ResponseBody
	public String autocomplete() {
		
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		
		data.put("china");
		data.put("japan");
		data.put("america");
		
		json.put("data", data);
		
		return json.toString();
	}
	
	@RequestMapping("/dropdownlist")
	@ResponseBody
	public String dropdownlist() {
		
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		
		JSONObject jo1 = new JSONObject();
		jo1.put("ProductName", "Black");
		jo1.put("ProductID", "1");
		data.put(jo1);
		
		JSONObject jo2 = new JSONObject();
		jo2.put("ProductName", "Orange");
		jo2.put("ProductID", "2");
		data.put(jo2);
		
		JSONObject jo3 = new JSONObject();
		jo3.put("ProductName", "Pink");
		jo3.put("ProductID", "3");
		data.put(jo3);
		
		json.put("data", data);
		
		return json.toString();
	}
	
	@RequestMapping("/multilist")
	@ResponseBody
	public String multilist() {
		
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		
		JSONObject jo1 = new JSONObject();
		jo1.put("ProductName", "Black");
		jo1.put("ProductID", "1");
		data.put(jo1);
		
		JSONObject jo2 = new JSONObject();
		jo2.put("ProductName", "Orange");
		jo2.put("ProductID", "2");
		data.put(jo2);
		
		JSONObject jo3 = new JSONObject();
		jo3.put("ProductName", "Pink");
		jo3.put("ProductID", "3");
		data.put(jo3);
		
		json.put("data", data);
		
		return json.toString();
	}
	
	@RequestMapping("/treeData")
	@ResponseBody
	public String treeData() {
		
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		
		JSONObject jo1 = new JSONObject();
		jo1.put("text", "Item 1");
		data.put(jo1);
		
		JSONObject jo2 = new JSONObject();
		jo2.put("text", "Item 2");
		JSONArray jo2a =  new JSONArray();
		
		JSONObject jo2a1 = new JSONObject();
		jo2a1.put("text", "Item 2-1");
		jo2a.put(jo2a1);
		JSONObject jo2a2 = new JSONObject();
		jo2a2.put("text", "Item 2-2");
		jo2a.put(jo2a2);
		
		jo2.put("items", jo2a);
		
		data.put(jo2);
		
		JSONObject jo3 = new JSONObject();
		jo3.put("text", "Item 3");
		data.put(jo3);
		
		json.put("data", data);
		
		return json.toString();
	}
	
	@RequestMapping("/chartdata")
	@ResponseBody
	public String chartdata() {

		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		
		JSONObject jo1 = new JSONObject();
		jo1.put("year", "2011");
		jo1.put("nuclear", 56000);
		jo1.put("hydro", 63000);
		data.put(jo1);
		
		
		JSONObject jo2 = new JSONObject();
		jo2.put("year", "2012");
		jo2.put("nuclear", 74000);
		jo2.put("hydro", 91000);
		data.put(jo2);
		
		JSONObject jo3 = new JSONObject();
		jo3.put("year", "2013");
		jo3.put("nuclear", 117000);
		jo3.put("hydro", 138000);
		data.put(jo2);
		
		
		json.put("data", data);
		
		return json.toString();
	}
	
	@RequestMapping("/ganttList")
	@ResponseBody
	public String ganttList() {
	     JSONArray data =  new JSONArray();
	     GanttTask task =  new GanttTask();
	     task.setId(1L);
	     task.setTitle("Software validation");
	     task.setParentId(null);
	     task.setOrderId(0L);
	     task.setStart("2016-06-01");
	     task.setEnd("2016-06-10");
	     task.setPercentComplete(0.88);
	     task.setSummary(true);
	     task.setExpanded(true);
	     data.put(task.listJson());
	     
	     task= new GanttTask();
	     
	     task.setId(11L);
	     task.setTitle("Research");
	     task.setParentId(1L);
	     task.setOrderId(1L);
	     task.setStart("2016-06-12");
	     task.setEnd("2016-06-18");
	     task.setPercentComplete(0.43);
	     task.setSummary(true);
	     task.setExpanded(true);
	     data.put(task.listJson());
	     
			
		return data.toString();
		
	}
	
	@RequestMapping("/ganttDependency")
	@ResponseBody
	public String ganttDependency() {
	     JSONArray data =  new JSONArray();
	     GanttDependencies gentt =  new GanttDependencies();
	     gentt.setId(528L);
	     gentt.setPredecessorId(1L);
	     gentt.setSuccessorId(11L);
	     gentt.setType(1L);
	   
	     data.put(gentt.listJson());
			
		return data.toString();
		
	}
	
	@RequestMapping("/spreadSheet")
	public String spreadSheet() {
		return "spreadSheet";
	}
	
	@RequestMapping("/spreadSheetData")
	@ResponseBody
	public String spreadSheetData() {
		JSONArray data = new JSONArray();
		
		JSONObject object = new JSONObject();
		object.put("ProductID", "1");
		object.put("ProductName", "Chai");
		object.put("UnitPrice", "18");
		object.put("UnitsInStock", "39");
		object.put("Discontinued", false);
		data.put(object);
		
		object =  new JSONObject();
		object.put("ProductID", "2");
		object.put("ProductName", "Chang");
		object.put("UnitPrice", "19");
		object.put("UnitsInStock", "17");
		object.put("Discontinued", false);
		data.put(object);
		
		object =  new JSONObject();
		object.put("ProductID", "3");
		object.put("ProductName", "Chang");
		object.put("UnitPrice", "19");
		object.put("UnitsInStock", "17");
		object.put("Discontinued", false);
		data.put(object);

		JSONObject json = new JSONObject();
		json.put("data", data);
		
		return json.toString();
	}
}