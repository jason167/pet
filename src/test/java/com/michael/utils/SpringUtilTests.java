package com.michael.utils;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.michael.po.Cat;

@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = {"classpath:META-INF/spring-util.xml"}) 
public class SpringUtilTests extends AbstractJUnit4SpringContextTests{

	
	@Resource
	private Cat cat;
	
	@Test
	public void testCatBean(){
		if (cat == null) {
			System.out.println("cat bean is null");
		}else{
			System.out.println("cat:" + cat);
		}
	}
}
