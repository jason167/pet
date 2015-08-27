package com.michael.remote;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.util.ClassUtils;

public abstract class RemoteExporterSupport implements BeanClassLoaderAware{

	ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
	
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		// TODO Auto-generated method stub
		this.defaultClassLoader = classLoader;
	}
	
	public ClassLoader getBeanClassLoader() {
		return defaultClassLoader;
	}
	
}
