package com.michael.utils;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

@Service("petInitAware")
public class PetInitAware implements InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
		listBean();
	}
	
	void listBean(){
		String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, Object.class);
		for (String name : beanNames) {
			logger.info("beanNames = name:{}", name);
		}
		
		Map<String, Object> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, Object.class, true, false);
		Iterator<String> iterator = matchingBeans.keySet().iterator();
		
		while(iterator.hasNext()){
			String name = iterator.next();
			logger.info("HandlerMapping = name:{}, bean:{}", name, matchingBeans.get(name));
		}
	}
	
	public void regService(Class<?> cls){
		try {
			ConfigurableApplicationContext confCtx = (ConfigurableApplicationContext) this.applicationContext;
			if (confCtx.getBeanFactory() instanceof BeanDefinitionRegistry) {
				BeanDefinitionRegistry registry = (BeanDefinitionRegistry) confCtx.getBeanFactory();
				
				String name = cls.getSimpleName();
				logger.info("name:{}, class:{}", name, cls);
				
				// 注册
				BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(cls);
				BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinitionBuilder.getRawBeanDefinition(), name);
				BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
				
				Object bean = this.applicationContext.getBean(name);
				logger.info("bean:{}", bean);
			} 
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		regService(Thread.currentThread().getContextClassLoader().loadClass(Common.class.getName()));
	}

}
