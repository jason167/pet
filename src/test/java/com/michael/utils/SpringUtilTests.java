package com.michael.utils;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = {"classpath:META-INF/spring-util.xml"}) 
public class SpringUtilTests extends AbstractJUnit4SpringContextTests{

}
