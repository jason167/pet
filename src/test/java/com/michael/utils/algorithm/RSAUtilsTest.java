package com.michael.utils.algorithm;

import javax.annotation.Resource;

import org.junit.Test;

import com.michael.utils.SpringUtilTests;

public class RSAUtilsTest extends SpringUtilTests{

	@Resource
	RSAUtil rsaUtil;
	
	@Test
	public void testRsa(){
		
		
		try {
			String ming = "123456789abcd啊保存到";
			String mi = rsaUtil.encrypt(ming);
			System.err.println("mi:" + mi);
			mi = rsaUtil.encrypt(ming);
			System.err.println("mi:" + mi);
			System.err.println("mi.length:" + mi.length());
			
			ming = rsaUtil.decrypt(mi);
			System.err.println("ming:" + ming);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
