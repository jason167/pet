package com.michael.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

public class PetMethod {

	private Method method;
	private Object target;
	
	public PetMethod(Method method, Object target) {
		// TODO Auto-generated constructor stub
		this.method = method;
		this.target = target;
	}
	
	public Object invoke(String msg, HttpServletRequest httpRequest) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		return method.invoke(target, msg);
	}
	
	
	
	
}
