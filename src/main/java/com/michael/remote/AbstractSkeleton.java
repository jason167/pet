package com.michael.remote;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过给定的接口构造函数列表，用于根据请求消息定位目标服务；
 * 消息需要序列化和对应的反序列化，即通过client的接口调用 + proxy构造一条包含method+params的标记；
 * 在service端通过proxy进行反序列化，得到这个method+params的标记，在本类调用getMethod(string)获得对应的服务。
 * 
 * 可以参考一下的类，将其修改为注解的方式，以适用于标准的http协议:
 * @see com.michael.utils.PetBeanDefinitionRegistryPostProcessor
 *
 *
 * @version $Id: AbstractSkeleton.java 2015年8月24日 下午5:45:59 $
 */
abstract public class AbstractSkeleton {
	  private Class _apiClass;
	  private Class _homeClass;
	  private Class _objectClass;
	  
	  private Logger logger = LoggerFactory.getLogger(getClass());
	  
	  private HashMap _methodMap = new HashMap();

	  /**
	   * Create a new hessian skeleton.
	   *
	   * @param apiClass the API interface
	   */
	  protected AbstractSkeleton(Class apiClass)
	  {
	    _apiClass = apiClass;
	    
	    Method []methodList = apiClass.getMethods();

	    for (int i = 0; i < methodList.length; i++) {
	      Method method = methodList[i];
	      logger.info("apiClass:{}, method:{}", apiClass.getName(), method.getName());
	      if (_methodMap.get(method.getName()) == null)
	        _methodMap.put(method.getName(), methodList[i]);

	      Class []param = method.getParameterTypes();
	      String mangledName = method.getName() + "__" + param.length;
	      _methodMap.put(mangledName, methodList[i]);
	      
	      _methodMap.put(mangleName(method, false), methodList[i]);
	    }
	  }

	  /**
	   * Returns the API class of the current object.
	   */
	  public String getAPIClassName()
	  {
	    return _apiClass.getName();
	  }

	  /**
	   * Returns the API class of the factory/home.
	   */
	  public String getHomeClassName()
	  {
	    if (_homeClass != null)
	      return _homeClass.getName();
	    else
	      return getAPIClassName();
	  }

	  /**
	   * Sets the home API class.
	   */
	  public void setHomeClass(Class homeAPI)
	  {
	    _homeClass = homeAPI;
	  }

	  /**
	   * Returns the API class of the object URLs
	   */
	  public String getObjectClassName()
	  {
	    if (_objectClass != null)
	      return _objectClass.getName();
	    else
	      return getAPIClassName();
	  }

	  /**
	   * Sets the object API class.
	   */
	  public void setObjectClass(Class objectAPI)
	  {
	    _objectClass = objectAPI;
	  }

	  /**
	   * Returns the method by the mangled name.
	   *
	   * @param mangledName the name passed by the protocol
	   */
	  protected Method getMethod(String mangledName)
	  {
	    return (Method) _methodMap.get(mangledName);
	  }

	  /**
	   * Creates a unique mangled method name based on the method name and
	   * the method parameters.
	   *
	   * @param method the method to mangle
	   * @param isFull if true, mangle the full classname
	   *
	   * @return a mangled string.
	   */
	  public static String mangleName(Method method, boolean isFull)
	  {
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append(method.getName());
	    
	    Class []params = method.getParameterTypes();
	    for (int i = 0; i < params.length; i++) {
	      sb.append('_');
	      sb.append(mangleClass(params[i], isFull));
	    }

	    return sb.toString();
	  }

	  /**
	   * Mangles a classname.
	   */
	  public static String mangleClass(Class cl, boolean isFull)
	  {
	    String name = cl.getName();

	    if (name.equals("boolean") || name.equals("java.lang.Boolean"))
	      return "boolean";
	    else if (name.equals("int") || name.equals("java.lang.Integer")
	             || name.equals("short") || name.equals("java.lang.Short")
	             || name.equals("byte") || name.equals("java.lang.Byte"))
	      return "int";
	    else if (name.equals("long") || name.equals("java.lang.Long"))
	      return "long";
	    else if (name.equals("float") || name.equals("java.lang.Float")
	             || name.equals("double") || name.equals("java.lang.Double"))
	      return "double";
	    else if (name.equals("java.lang.String")
	             || name.equals("com.caucho.util.CharBuffer")
	             || name.equals("char") || name.equals("java.lang.Character")
	             || name.equals("java.io.Reader"))
	      return "string";
	    else if (name.equals("java.util.Date")
	             || name.equals("com.caucho.util.QDate"))
	      return "date";
	    else if (InputStream.class.isAssignableFrom(cl)
	             || name.equals("[B"))
	      return "binary";
	    else if (cl.isArray()) {
	      return "[" + mangleClass(cl.getComponentType(), isFull);
	    }
	    else if (name.equals("org.w3c.dom.Node")
	             || name.equals("org.w3c.dom.Element")
	             || name.equals("org.w3c.dom.Document"))
	      return "xml";
	    else if (isFull)
	      return name;
	    else {
	      int p = name.lastIndexOf('.');
	      if (p > 0)
	        return name.substring(p + 1);
	      else
	        return name;
	    }
	  }

	  public String toString()
	  {
	    return getClass().getSimpleName() + "[" + _apiClass.getName() + "]";
	  }
}
