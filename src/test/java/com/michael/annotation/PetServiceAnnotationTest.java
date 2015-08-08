package com.michael.annotation;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.michael.proxy.service.impl.Hello;
import com.michael.utils.DaoException;
import com.michael.utils.JsonUtil;
import com.michael.utils.PetAware;
import com.michael.utils.PetInitAware;
import com.michael.utils.PetMethod;

public class PetServiceAnnotationTest  
 {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private PetAware petServiceHolder;
	ApplicationContext context;
	
	@Before
	public void init(){
		try {
			logger.info("init annotation test");
			context = new ClassPathXmlApplicationContext("META-INF/application.xml");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
	}
	
	@Test
	public void testRegService(){
		logger.info("===============testRegService");
		PetInitAware petInitAware = (PetInitAware) context.getBean("petInitAware");
		try {
			petInitAware.regService(Hello.class);
			logger.info("Done!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void r(){
		try {
			
			petServiceHolder = context.getBean("petServiceHolder", PetAware.class);
			Assert.notNull(petServiceHolder, "ssdServiceHolder must not be null");
			
			String serviceType = "hello";
			String version = "1.0.0";
			PetMethod petMethod = petServiceHolder.getServiceMethod(serviceType, version);
			logger.info("{}", petMethod.invoke("Michael", null));
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
		
	}

}
