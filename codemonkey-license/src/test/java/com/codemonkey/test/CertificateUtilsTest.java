package com.codemonkey.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Test;

import com.codemonkey.utils.CertificateUtils;

public class CertificateUtilsTest extends CertificateTest{

	@Test
	public void simple() throws Exception {
		System.err.println("公钥加密——私钥解密");
		String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
		byte[] data = source.getBytes();

		byte[] encrypt = CertificateUtils.encryptByPublicKey(data, certificatePath);

		byte[] decrypt = CertificateUtils.decryptByPrivateKey(encrypt, keyStorePath, alias, password);
		String outputStr = new String(decrypt);

		System.out.println("加密前: \r\n" + source + "\r\n" + "解密后: \r\n" + outputStr);

		// 验证数据一致
		assertArrayEquals(data, decrypt);

		// 验证证书有效
		//assertTrue(CertificateUtils.verifyCertificate(certificatePath));
	}

	@Test
	public void simpleSign() throws Exception {
		System.err.println("私钥加密——公钥解密");

		String source = "这是一行签名的测试文字";
		byte[] data = source.getBytes();

		byte[] encodedData = CertificateUtils.encryptByPrivateKey(data, keyStorePath, alias, password);

		byte[] decodedData = CertificateUtils.decryptByPublicKey(encodedData, certificatePath);

		String target = new String(decodedData);
		System.out.println("加密前: \r\n" + source + "\r\n" + "解密后: \r\n" + target);
		assertEquals(source, target);

		System.err.println("私钥签名——公钥验证签名");
		// 产生签名
		String sign = CertificateUtils.signToBase64(encodedData, keyStorePath, alias, password);
		System.out.println("签名:\r\n" + sign);

		// 验证签名
		boolean status = CertificateUtils.verifySign(encodedData, sign, certificatePath);
		System.err.println("状态:\r\n" + status);
		assertTrue(status);
	}

	@Test
	public void testFileSign() throws Exception {
		String filePath = "readme.txt";
		String sign = CertificateUtils.signFileToBase64(filePath, keyStorePath,alias, password);
		System.err.println("生成签名：\r\n" + sign);
		boolean result = CertificateUtils.validateFileSign(filePath, sign,certificatePath);
		System.err.println("校验结果：" + result);
	}
	
	@Test
	public void testShowCertificateInfo() throws Exception{
		CertificateUtils.showCertificateInfo(certificatePath);
	}
	
	@Test
	public void testVerify() throws Exception{
		DateTime dateTime = new DateTime("2017-3-24");
		boolean flag = CertificateUtils.verifyCertificate(dateTime.toDate(), certificatePath);
		assertEquals(false, flag);
	}

}
