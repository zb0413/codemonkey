package com.codemonkey.test;

import java.io.IOException;
import java.security.KeyStoreException;

import org.junit.Test;

import com.codemonkey.utils.CertificateGenerator;

public class CertificateGeneratorTest extends CertificateTest{

	@Test
	public void testKeyStore() throws KeyStoreException, IOException{
		
		CertificateGenerator.genKeyStore();
		
	}
}
