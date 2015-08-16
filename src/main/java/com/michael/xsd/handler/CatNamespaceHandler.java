package com.michael.xsd.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.michael.xsd.parser.CatBeanDefinitionParser;

public class CatNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		registerBeanDefinitionParser("cat", new CatBeanDefinitionParser());
	}

}
