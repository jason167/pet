package com.michael.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleFilter implements Filter {

	private String filterName;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
	public String getFilterName() {
		return filterName;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		logger.info("{} init...", getFilterName());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		logger.info("{} doFilter...", getFilterName());
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		logger.info("{} destroy...", getFilterName());
	}

}
