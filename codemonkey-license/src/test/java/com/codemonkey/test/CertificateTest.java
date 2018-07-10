package com.codemonkey.test;

import org.junit.Before;

public class CertificateTest {

	private static final String KEY_STORE_NAME = "abcd.keystore";
	private static final String CERTIFICATE_NAME = "abcd.cer";
	public static final String password = "123456";
	public static final String alias = "www.abcd.com.cn";
	public static String certificatePath;
	public static String keyStorePath;

	@Before
	public void before() {
		String currentDir = CertificateUtilsTest.class.getResource("").getPath();
		if (currentDir.startsWith("/")){
			currentDir = currentDir.substring(1);
		}
			
		if (!currentDir.endsWith("/")){
			currentDir += "/";
		}
		keyStorePath = currentDir + KEY_STORE_NAME;
		certificatePath = currentDir + CERTIFICATE_NAME;
	}
	
}
