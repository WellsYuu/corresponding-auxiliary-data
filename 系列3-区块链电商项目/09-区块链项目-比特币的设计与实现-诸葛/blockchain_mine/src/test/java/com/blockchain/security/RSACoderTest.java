package com.blockchain.security;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * 非对称加密测试
 */
public class RSACoderTest {
	private String publicKey;
	private String privateKey;

	@Before
	public void setUp() throws Exception {
		Map<String, Object> keyMap = RSACoder.initKey();

		publicKey = RSACoder.getPublicKey(keyMap);
		privateKey = RSACoder.getPrivateKey(keyMap);
		System.err.println("公钥: \n\r" + publicKey);
		System.err.println("私钥： \n\r" + privateKey);
	}

	@Test
	public void testEncrypt() throws Exception {
		System.err.println("公钥加密——私钥解密");
		String inputStr = "abc";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

		byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, privateKey);

		String outputStr = new String(decodedData);
		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
		assertEquals(inputStr, outputStr);

	}

	@Test
	public void testSign() throws Exception {
		System.err.println("私钥签名——公钥验证签名");
		String inputStr = "sign";
		byte[] data = inputStr.getBytes();

		// 产生签名
		String sign = RSACoder.sign(data, privateKey);
		System.err.println("签名:\r" + sign);

		// 验证签名
		boolean status = RSACoder.verify(data, publicKey, sign);
		System.err.println("状态:\r" + status);
		assertTrue(status);

	}

}
