package com.michael.remote;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;

public abstract class RemoteExporterSupport implements BeanClassLoaderAware, ApplicationContextAware{

	ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
	ApplicationContext applicationContext;
	
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		// TODO Auto-generated method stub
		this.defaultClassLoader = classLoader;
	}
	
	public ClassLoader getBeanClassLoader() {
		return defaultClassLoader;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
		initApplicationContext();
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	protected abstract void initApplicationContext();
}
