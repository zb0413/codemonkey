package com.codemonkey.test;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.GlobalConfig;
import com.codemonkey.service.GlobalConfigService;

public class AuditingTest extends CoreAppTest{

	@Autowired private GlobalConfigService globalConfigService;
	
	@Test
	public void testAuditing() {
		GlobalConfig g = new GlobalConfig();
		globalConfigService.save(g);
		assertThat(g.getCreationDate()).isNotNull();
		assertThat(g.getModificationDate()).isNotNull();
	}
}
