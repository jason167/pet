package com.michael.utils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import com.michael.annotation.PetField;
import com.michael.annotation.PetServiceExporter;

public abstract class PetServiceAware implements /*BeanNameAware,InitializingBean */ DisposableBean, ApplicationContextAware, BeanPostProcessor, PetAware {

	private ApplicationContext applicationContext;
	protected final Map<String, PetMethod> petServiceCache = new HashMap<String, PetMethod>();
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		//logger.info("=====Before========beanName:{}", beanName);
		return bean;
	}
	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		petServiceCache.clear();
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		logger.info("=====After========beanName:{}", beanName);
		if (applicationContext.containsBean(beanName)) {
			PetServiceExporter petServiceExporter = applicationContext.findAnnotationOnBean(beanName, PetServiceExporter.class);
			if (petServiceExporter != null) {
				Method[] methods = bean.getClass().getDeclaredMethods();
				for (Method method : methods) {
					PetField petField = AnnotationUtils.findAnnotation(method, PetField.class);
					if (petField != null) {
						
						logger.info("=======" + petField.value().toString() + ":" + petField.version());
						List<String> serviceTypeList = Arrays.asList(petField.value());
						for (String serviceType : serviceTypeList) {
							logger.info("---------servicetype:{}", serviceType);
							petServiceCache.put(mangleName(serviceType, petField.version()), new PetMethod(method, bean));
						}
						
					}
				}
			}
			
		}
		return bean;
	}

	protected String mangleName(String value, String version) {
		// TODO Auto-generated method stub
		if (version == null || version.trim().equals("")) {
			return value;
		}
		return value +"_"+ version;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
	
	public abstract PetMethod getServiceMethod(String serviceType, String version);
}
