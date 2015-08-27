package com.michael.service.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.util.NestedServletException;

import com.michael.remote.CatHttpExporter;

public class CatHttpServiceExporter extends CatHttpExporter implements
		HttpRequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (!"GET".equals(request.getMethod())) {
			throw new HttpRequestMethodNotSupportedException(request.getMethod(),
					new String[] {"GET"}, "CatServiceExporter only supports GET requests");
		}
		
		try {
		  invoke(request, response);
		}
		catch (Throwable ex) {
		  throw new NestedServletException("Cat skeleton invocation failed", ex);
		}
	}

}
