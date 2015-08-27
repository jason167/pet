package com.michael.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Service;

import com.michael.service.ICatService;
import com.michael.service.impl.CatHttpServiceExporter;
import com.michael.service.impl.CatServiceImpl;

@Service
public class CatHttpExporterRegistryPostProcessor implements
		BeanDefinitionRegistryPostProcessor {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub

	}

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub
		logger.info("postProcessBeanDefinitionRegistry");
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(CatHttpServiceExporter.class);
		beanDefinitionBuilder.addPropertyValue("service", new CatServiceImpl());
		beanDefinitionBuilder.addPropertyValue("serviceInterface", ICatService.class);
		beanDefinitionBuilder.getRawBeanDefinition().setResourceDescription(CatServiceImpl.class.getName());
		beanDefinitionBuilder.getRawBeanDefinition().setSource(CatServiceImpl.class);
		BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinitionBuilder.getRawBeanDefinition(), "/catCtrl");
		BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
		
	}

}
