package com.michael.service.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.cglib.proxy.MethodInterceptor;
//import org.springframework.cglib.proxy.MethodProxy;

public class LoggerIntercept implements MethodInterceptor {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		logger.info("object:{}, method:{}, object[]:{}, methodproxy:{}",
				new Object[] { invocation.getThis(), invocation.getMethod(),
						invocation.getArguments() });
		Object value = invocation.getMethod().invoke(invocation.getThis(),
				invocation.getArguments());
		logger.info("value:{}", value);
		return value;
	}

	// @Override
	// public Object intercept(Object arg0, Method arg1, Object[] arg2,
	// MethodProxy arg3) throws Throwable {
	// // TODO Auto-generated method stub
	//
	// logger.info("object:{}, method:{}, object[]:{}, methodproxy:{}",
	// new Object[]{arg0, arg1, arg2, arg3});
	// Object value = arg1.invoke(arg0, arg2);
	//
	// logger.info("value:{}", value);
	//
	// return value;
	// }

}
