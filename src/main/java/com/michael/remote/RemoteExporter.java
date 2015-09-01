package com.michael.remote;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.remoting.support.RemoteInvocationTraceInterceptor;
import org.springframework.util.ClassUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RemoteExporter extends RemoteExporterSupport{

	private Object service;
	private Class serviceInterface;
	private List<Object> interceptors = Lists.newArrayList();
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public List<Object> getInterceptors() {
		return interceptors;
	}
	
	public void setService(Object service) {
		this.service = service;
	}
	
	public Object getService() {
		return service;
	}
	
	public void setServiceInterface(Class serviceInterface) {
		this.serviceInterface = serviceInterface;
	}
	
	public Class getServiceInterface() {
		return serviceInterface;
	}
	
	/**
	 * Check whether the service reference has been set.
	 * @see #setService
	 */
	protected void checkService() throws IllegalArgumentException {
		if (getService() == null) {
			throw new IllegalArgumentException("Property 'service' is required");
		}
	}
	
	/**
	 * Check whether a service reference has been set,
	 * and whether it matches the specified service.
	 * @see #setServiceInterface
	 * @see #setService
	 */
	protected void checkServiceInterface() throws IllegalArgumentException {
		Class serviceInterface = getServiceInterface();
		Object service = getService();
		if (serviceInterface == null) {
			throw new IllegalArgumentException("Property 'serviceInterface' is required");
		}
		if (service instanceof String) {
			throw new IllegalArgumentException("Service [" + service + "] is a String " +
					"rather than an actual service reference: Have you accidentally specified " +
					"the service bean name as value instead of as reference?");
		}
		if (!serviceInterface.isInstance(service)) {
			throw new IllegalArgumentException("Service interface [" + serviceInterface.getName() +
					"] needs to be implemented by service [" + service + "] of class [" +
					service.getClass().getName() + "]");
		}
	}
	
	/**
	 * Get a proxy for the given service object, implementing the specified
	 * service interface.
	 * <p>Used to export a proxy that does not expose any internals but just
	 * a specific interface intended for remote access. Furthermore, a
	 * {@link RemoteInvocationTraceInterceptor} will be registered (by default).
	 * @return the proxy
	 * @see #setServiceInterface
	 * @see #setRegisterTraceInterceptor
	 * @see RemoteInvocationTraceInterceptor
	 */
	protected Object getProxyForService() {
		checkService();
		checkServiceInterface();
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.addInterface(getServiceInterface());
		proxyFactory.setTarget(getService());
		if (this.getInterceptors() != null) {
			AdvisorAdapterRegistry adapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();
			for (Object interceptor : getInterceptors()) {
				proxyFactory.addAdvisor(adapterRegistry.wrap(interceptor));
			}
		}
		proxyFactory.setOpaque(true);
		return proxyFactory.getProxy(getBeanClassLoader());
	}

	
	private void testBeanFactoryUtils(){
		String[] objectBeans = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(), Object.class);
		HashMap<String, Object> xxMaps = Maps.newHashMap();
		for (String beanName : objectBeans) {
			Class<?> type = getApplicationContext().getType(beanName);
			if (isHandler(type)) {
				xxMaps.put(beanName, getApplicationContext().getBean(beanName));
			}
		}
		
	}
	

	protected boolean isHandler(Type type) {
		// TODO Auto-generated method stub
		Class cls = null;
		if(type instanceof Class){
			cls = (Class) type;
		}else{
			cls = type.getClass();
		}
		
		Class<?>[] allInterfaces =cls.getInterfaces();
		if(allInterfaces != null && allInterfaces.length > 0){
			for (Class<?> i : allInterfaces) {
				boolean assignable = ClassUtils.isAssignable(i, Advice.class);
				logger.info("===========type:{},  cls:{} == Advice.class", type, i.getName());
				if (assignable) {
					logger.info("************type:{},  cls:{} == Advice.class", type, i.getName());
					return true;
				}
				
				Type[] interfaces = i.getGenericInterfaces();
				if (interfaces != null && interfaces.length > 0) {
					for (Type ii : interfaces) {
						if (ii != null && !ii.equals(Object.class)) {
							boolean result = isHandler(ii);
							if (result) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
//		return ((AnnotationUtils.findAnnotation(type, Controller.class) != null) ||
//				(AnnotationUtils.findAnnotation(type, RequestMapping.class) != null));
	}


	/**
	 * 这里的Adevice可以指定任意Object.class，即取得上下文中所有Bean；
	 * 然后在通过<code> @see testBeanFactoryUtils()</code>的逻辑进行过滤，这样各功能类可以准确拿到目标advice。
	 * 可以参考如下类的实现：
	 * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping#isHandler(Class beanType)
	 * @see org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping#determineUrlsForHandler(String beanName)
	 * @see org.springframework.web.servlet.DispatcherServlet#initHandlerMappings(ApplicationContext context)
	 * @see org.springframework.web.servlet.DispatcherServlet#initHandlerAdapters(ApplicationContext context)
	 */
	private void initInterceptors() {
		// TODO Auto-generated method stub
		/**
		 * 针对特定的功能，可直接使用如下方式
		 */
//		Map<String, Advice> interceptorBeans =
//				BeanFactoryUtils.beansOfTypeIncludingAncestors(getApplicationContext(), Advice.class, true, false);
//		getInterceptors().addAll(interceptorBeans.values());
		
		
		/**
		 * DEMO,目前主要用于子类的重载
		 */
		Map<String, Object> interceptorBeans =
				BeanFactoryUtils.beansOfTypeIncludingAncestors(getApplicationContext(), Object.class, true, false);
		if (interceptorBeans != null) {
			Iterator<String> iterator = interceptorBeans.keySet().iterator();
			while (iterator.hasNext()) {
				String beanName = (String) iterator.next();
				Class<?> type = getApplicationContext().getType(beanName);
				if (isHandler(type)) {
					getInterceptors().add(interceptorBeans.get(beanName));
				}
			}
		}
		logger.info("getInterceptors.size : {}", getInterceptors().size());
//		BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(), MethodInterceptor.class);
//		testBeanFactoryUtils();
	}

	@Override
	void initApplicationContext() {
		// TODO Auto-generated method stub
		logger.info("=================initApplicationContext");
		initInterceptors();
	}
	
	
	
	

}
