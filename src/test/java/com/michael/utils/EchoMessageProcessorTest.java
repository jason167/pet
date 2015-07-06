package com.michael.utils;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * lookup-method fun test
 *
 */
public class EchoMessageProcessorTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	ApplicationContext context;
	
	@Before
	public void init(){
		try {
			logger.info("init echo message processor");
			context = new ClassPathXmlApplicationContext("META-INF/spring-util.xml");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
	}
	
	@Test
	public void testEcho(){
		
		if (context == null) {
			logger.info("application init failed !!");
			return;
		}
		
		EchoMessageProcessor messageProcessor = context.getBean("echoMessageProcessor", EchoMessageProcessor.class);
		for (int i = 0; i < 3; i++) {
			logger.info("{}", messageProcessor.echo());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@After
	public void destroy(){
		logger.info("destroy!");
	}
}
