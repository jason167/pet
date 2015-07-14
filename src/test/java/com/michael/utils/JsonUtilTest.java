package com.michael.utils;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtilTest extends SpringUtilTests {

	@Resource
	public JsonUtil jsonUtil;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("static-access")
	@Test
	public void testJson(){
		
		try {
			String dateFormat = "yyyy-MM-dd HH:mm:ss";
			UserDoma user = new UserDoma("zs", 18, true, "shenzhen");
			String userJson = jsonUtil.toJSONString(user, dateFormat);
			logger.info("userJson:{}", userJson);
			logger.info("user:{}", jsonUtil.parse(userJson));
			
			UserDoma u2 = jsonUtil.parse(userJson, UserDoma.class);
			logger.info("u2:{}", u2);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
		
	}
	
}
