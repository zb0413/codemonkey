package com.codemonkey.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.codemonkey.config.SecurityConfig;

public class SecurityTest extends JwtAppTest{

	@Autowired private MockMvc mvc;
	
	@Autowired private SecurityConfig securityConfig;
	
	@Test
	public void testLogin() throws Exception{
		mvc.perform(post(securityConfig.getLoginUrl())
				.param("username", "admin").param("password", "admin")
				.accept(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isOk()).andExpect(jsonPath("$.result").exists()).andExpect(jsonPath("$.success").value("true"));
	}
	
	@Test
	public void testAccessDeny() throws Exception{
		String url = "/test/accessDeny";
		mvc.perform(post(url)
				.accept(MediaType.TEXT_PLAIN)
				.accept(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().is(200)).andExpect(jsonPath("$.success").value("false")).andExpect(jsonPath("$.code").value("403"));
	}
	
	@Test
	public void testAccessPublic() throws Exception{
		String url = "/public/index.html";
		mvc.perform(post(url).accept(MediaType.TEXT_PLAIN)).andExpect(status().is(200));
	}
	
}
