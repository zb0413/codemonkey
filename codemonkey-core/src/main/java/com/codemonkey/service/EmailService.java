package com.codemonkey.service;

import java.io.File;
import java.util.List;

import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public interface EmailService {

	void send(SimpleEmail email);
	
	void send(MultiPartEmail email , List<File> files);
}
