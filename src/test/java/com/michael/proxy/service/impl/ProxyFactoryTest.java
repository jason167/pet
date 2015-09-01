package com.michael.proxy.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.InvocationHandler;

import org.aopalliance.aop.Advice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ClassUtils;

import com.google.common.collect.Lists;
import com.michael.proxy.service.IHello;
import com.michael.proxy.service.IWorld;
import com.michael.service.interceptor.LoggerIntercept;
import com.michael.service.interceptor.LoggerMethodBeforeAdvice;

@ContextConfiguration(locations = { "classpath*:META-INF/application.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ProxyFactoryTest implements BeanClassLoaderAware{

	private ClassLoader classLoader;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ArrayList<Advice> interceptors;
	
	@Test
	public void testHelloWorld(){
		try {
			resetInterceptors();
			
			IHello hello = (IHello) getProxy(new Hello());
			logger.info("=={}", hello.say("Terasa"));
			
			IWorld world = (IWorld) getProxy(new Hello());
			logger.info("=={}", world.hi("Michael"));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
	}
	
	@Test
	public void testWorld(){
		try {
			IWorld service = (IWorld) getProxy(new World());
			String hi = service.hi("World");
			logger.info("=={}", hi);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
		
	}
	
	private Object getProxy(Object object){
		ProxyFactory proxyFactory = new ProxyFactory();
		Class<?>[] allInterfaces = ClassUtils.getAllInterfaces(object);
		for (Class<?> ifs : allInterfaces) {
			logger.info("**************ifs name:{}", ifs.getName());
			proxyFactory.addInterface(ifs);
		}
		proxyFactory.setTarget(object);
		
		ArrayList<Advice> advices = getInterceptors();
		if (advices != null) {
			AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();
			for (Advice advice : advices) {
				proxyFactory.addAdvisor(advisorAdapterRegistry.wrap(advice));
			}
		}
		return proxyFactory.getProxy(getClassLoader());
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		// TODO Auto-generated method stub
		this.classLoader = classLoader;
	}
	
	public ClassLoader getClassLoader() {
		return this.classLoader;
	}
	
	public ArrayList<Advice> getInterceptors() {
		return this.interceptors;
	}
	
	private void resetInterceptors(){
		this.interceptors = Lists.newArrayList(new LoggerIntercept(), new LoggerMethodBeforeAdvice());
	}
	
	
	private static final class xxBridge implements InvocationHandler{

		private Object target;
		public xxBridge(Object target) {
			// TODO Auto-generated constructor stub
			this.target = target;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			// TODO Auto-generated method stub
			Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
			return targetMethod.invoke(target, args);
		}
		
	}
	
}


