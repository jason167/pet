package com.michael.utils;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;
import com.michael.po.Pet;
import com.michael.service.PetService;

public class HessianTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void testHessian(){
		String url = "http://localhost:8080/pet/xx";

		/**
		 * HessianServlet
		 */
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true);
		
		try {
			PetService pet = (PetService) factory.create(PetService.class, url);
			Pet pets = pet.findById(1);
			
			logger.info("{}", pets);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@After
	public void after(){
		logger.info("Done!");
	}

}
