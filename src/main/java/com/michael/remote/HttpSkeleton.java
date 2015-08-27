package com.michael.remote;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSkeleton extends AbstractSkeleton{

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Object service; 	// proxy
	private Class<?> apiClass; 	// interface

	public HttpSkeleton(Object service, Class<?> apiClass) {
		// TODO Auto-generated constructor stub
		super(apiClass);
		this.service = service;
		this.apiClass = apiClass;
	}
	
	void invoke(HttpServletRequest request, HttpServletResponse response){
		logger.info("req uri:{}", request.getRequestURI() );
		Enumeration<String> parameterNames = request.getParameterNames();
		StringBuffer params = new StringBuffer();
		while (parameterNames.hasMoreElements()) {
			String key = (String) parameterNames.nextElement();
			params.append(key).append("=").append(request.getParameter(key))
			.append(",");
		}
		
		logger.info("params = {}", params.toString());
		
		String serviceType = request.getParameter("serviceType");
//		String version = request.getParameter("version");
		
		Method method = getMethod(serviceType);
		try {
			Object value = "do nothing ...";
			if (method != null) {
				value = method.invoke(service, null);
			}
			response.getWriter().write(String.valueOf(value));
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
