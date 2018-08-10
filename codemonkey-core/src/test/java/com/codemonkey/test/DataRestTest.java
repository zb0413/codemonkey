package com.codemonkey.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class DataRestTest extends CoreAppTest{

	@Autowired private MockMvc mvc;
	
	private String testUrl = "/api/globalConfig";
	
	@Test
	public void testSave() throws Exception {
		
		String id = "1";
		//save
		JSONObject requestJson = new JSONObject();
		requestJson.put("id", "");
		requestJson.put("code", "code-1");
		mvc.perform(post(testUrl).contentType(MediaType.APPLICATION_JSON).content(requestJson.toString()))
		.andDo(print()).andExpect(status().isCreated());
		
		//read
		String readUrl = testUrl + "/" + id;
		mvc.perform(get(readUrl).contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.code").value("code-1"));
		
		//update
		String updateUrl = testUrl + "/" + id;
		requestJson = new JSONObject();
		requestJson.put("id", id);
		requestJson.put("code", "code-2");
		mvc.perform(put(updateUrl).contentType(MediaType.APPLICATION_JSON).content(requestJson.toString()))
		.andDo(print()).andExpect(status().isNoContent());
		
		//read for update
		mvc.perform(get(readUrl).contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.code").value("code-2"));
		
		//delete
		String deleteUrl = testUrl + "/" + id;
		mvc.perform(delete(deleteUrl).contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isNoContent());
	}
	
}
