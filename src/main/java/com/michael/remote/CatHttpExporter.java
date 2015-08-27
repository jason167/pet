package com.michael.remote;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;

public class CatHttpExporter extends RemoteExporter implements
		InitializingBean {

	private HttpSkeleton skeleton;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		prepare();
	}

	/**
	 * Initialize this exporter.
	 */
	public void prepare() {
		checkService();
		checkServiceInterface();
		this.skeleton = new HttpSkeleton(getProxyForService(), getServiceInterface());
	}
	
	public void invoke(HttpServletRequest request, HttpServletResponse response){
		this.skeleton.invoke(request, response);
	}

}
