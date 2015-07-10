package com.michael.utils;

import javax.annotation.Resource;

import org.junit.Test;

public class DESUtilTest extends SpringUtilTests {

	@Resource
	public DESUtil desUtil;
	
	@Test
	public void testDes(){
		String password = "12345678";
		String data = "abc123你好";
		byte[] encrypt = desUtil.encrypt(data.getBytes(), password);
		System.out.println(new String(encrypt));
		
		byte[] decrypt;
		try {
			decrypt = desUtil.decrypt(encrypt, password);
			System.out.println(new String(decrypt));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
