package com.codemonkey.extcmp;

import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

public class ExtGridColumn {

	private String dataIndex;
	
	private String text;
	
	private Boolean hidden = false;
	
	private ExtGridEditor editor;
	
	public ExtGridColumn(String dataIndex , String text){
		this.setDataIndex(dataIndex);
		this.setText(text);
	}
	
	public JSONObject json(){
		JSONObject jo = new JSONObject();
		
		jo.put("dataIndex", OgnlUtils.stringValue("dataIndex", this));
		jo.put("text", OgnlUtils.stringValue("text", this));
		jo.put("hidden", OgnlUtils.stringValue("hidden", this));
		if(editor != null){
			jo.put("editor", editor.json());
		}
		return jo;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public ExtGridEditor getEditor() {
		return editor;
	}

	public void setEditor(ExtGridEditor editor) {
		this.editor = editor;
	}
	
	
	
}
