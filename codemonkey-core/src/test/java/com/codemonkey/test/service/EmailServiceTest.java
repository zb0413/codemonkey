
package com.codemonkey.test.service;

//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.service.EmailService;
import com.codemonkey.test.CoreAppTest;

public class EmailServiceTest extends CoreAppTest{

	@Autowired EmailService emailService;
	
	@Test
	public void testSendEmail(){
//		SimpleEmail email = new SimpleEmail();
//		email.setSubject("test");
//		try {
//			email.addTo("zb@dliit.com");
//		} catch (EmailException e) {
//			e.printStackTrace();
//		}
//		emailService.send(email);
	}
	
}
