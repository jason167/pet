package com.michael.utils;

import javax.annotation.Resource;

import org.junit.Test;

public class AESUtilTest extends SpringUtilTests {

	@Resource
	public AESUtil aesUtil;
	
	@Test
	public void testAes(){
		String data = "abc123你好";
		String password = "12345678";
		byte[] encrypt = aesUtil.encrypt(data, password);
		byte[] decrypt = aesUtil.decrypt(encrypt, password);
		System.out.println(new String(decrypt));
	}
}
