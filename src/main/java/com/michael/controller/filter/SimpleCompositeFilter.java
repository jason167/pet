package com.michael.controller.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.CompositeFilter;

public class SimpleCompositeFilter extends CompositeFilter {

	
	@Override
	public void setFilters(List<? extends Filter> filters) {
		// TODO Auto-generated method stub
		System.out.println("setFileters");
		super.setFilters(filters);
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("SimpleCompositeFilter init");
		super.init(config);
	}
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.doFilter(request, response, chain);
	}
}
