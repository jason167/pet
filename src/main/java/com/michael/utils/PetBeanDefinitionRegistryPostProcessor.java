/**
 * Copyright (c) 2011-2015 All Rights Reserved.
 */
package com.michael.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author 610273
 * @version $Id: PetBeanDefinitionRegistryPostProcessor.java 2015年8月8日 上午11:56:24 $
 */
@Service
public class PetBeanDefinitionRegistryPostProcessor implements
		BeanDefinitionRegistryPostProcessor {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		
		if (beanFactory instanceof BeanDefinitionRegistry) {
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
			proc(registry, "postProcessBeanFactory");
		}
		
		
	}
	
	void proc(BeanDefinitionRegistry registry, String fromMethod){
		logger.info("========fromMethod:{}", fromMethod);
		logger.info("========registry.getBeanDefinitionCount:{}", registry.getBeanDefinitionCount());

		String[] names = registry.getBeanDefinitionNames();
		for (String name : names) {
			
			BeanDefinition beanDefinition = registry.getBeanDefinition(name);
			logger.info("========beanName:{}, beanDefinition.getBeanClassName:{}", name, beanDefinition.getBeanClassName());
		}
	}

	/** 
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry(org.springframework.beans.factory.support.BeanDefinitionRegistry)
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub
		proc(registry, "postProcessBeanDefinitionRegistry");
		
		
	}

}
