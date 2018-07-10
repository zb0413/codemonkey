package com.codemonkey.service;

import java.io.File;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.SysProp;
import com.codemonkey.error.SysError;
import com.codemonkey.utils.SysUtils;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired private SysProp sysProp;
	
	public void send(SimpleEmail email){
		try {
			
			setSenderInfo(email);
			
			validate(email);
			
			email.send();
		
		} catch (EmailException e) {
			e.printStackTrace();
		}
		
	}
	
	public void send(MultiPartEmail email , List<File> files){
		try {
			
			setSenderInfo(email);
			
			if(SysUtils.isNotEmpty(files)){
				for(File f : files){
					EmailAttachment attachment = new EmailAttachment();
					attachment.setPath(f.getAbsolutePath());
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setName(f.getName());
					
					email.attach(attachment);
				}
			}
	
			validate(email);
			
			email.send();
		
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	public void setSenderInfo(Email email){
		try {
			email.setHostName(sysProp.getSmtpHost());
			email.setSmtpPort(sysProp.getSmtpPort());
			email.setAuthenticator(new DefaultAuthenticator(sysProp.getSmtpUser(), sysProp.getSmtpPassword()));
			email.setSSLOnConnect(true);
			email.setFrom(sysProp.getSmtpUser());
			
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	public void validate(Email email){
		
		if(SysUtils.isEmpty(email.getSubject())){
			throw new SysError("email title is null");
		}
		
		if(SysUtils.isEmpty(email.getToAddresses())){
			throw new SysError("to email is empty");
		}
		
	}
	
}
