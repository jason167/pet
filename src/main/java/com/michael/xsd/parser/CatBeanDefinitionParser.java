package com.michael.xsd.parser;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import com.michael.po.Cat;

public class CatBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	private final static String PET_NAME = "petName";
	private final static String AGE = "age";
	@Override
	protected Class<?> getBeanClass(Element element) {
		// TODO Auto-generated method stub
		return Cat.class;
	}
	
	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		// TODO Auto-generated method stub
		String petName = element.getAttribute(PET_NAME);
		String age = element.getAttribute(AGE);
		builder.addPropertyValue(PET_NAME, petName);
		builder.addPropertyValue(AGE, age);
		
	}
	
}
