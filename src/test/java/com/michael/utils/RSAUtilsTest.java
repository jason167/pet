package com.michael.utils;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = {"classpath:META-INF/spring-util.xml"}) 
public class RSAUtilsTest extends AbstractJUnit4SpringContextTests {

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
