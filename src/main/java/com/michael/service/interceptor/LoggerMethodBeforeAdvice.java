package com.michael.service.interceptor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Service;

@Service
public class LoggerMethodBeforeAdvice implements MethodBeforeAdvice {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		// TODO Auto-generated method stub
		logger.info("logger befor, method:{}, args:{}, target:{}", method, args, target);
	}

}
