package com.michael.cache;

import java.util.Properties;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.michael.cache.ISpringDataRedisSessionDao;

@ContextConfiguration(locations = { "classpath*:META-INF/application.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDataRedisSessionDaoTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private ISpringDataRedisSessionDao redisSessionDao;
	private RedisTemplate redisTemplate;
	private String key = "uid";
	private String value = "michael";
	
	@Before
	public void init(){
		redisTemplate = redisSessionDao.getRedisTemplate();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRedisSet(){
		if (redisTemplate == null) {
			logger.error("redisTemplate is null");
			return;
		}
		
		try {
			redisTemplate.execute(new RedisCallback<Object>() {

				@Override
				public Object doInRedis(RedisConnection conn)
						throws DataAccessException {
					// TODO Auto-generated method stub
					conn.set(key.getBytes(), value.getBytes());
					Properties properties = redisTemplate.getConnectionFactory().getConnection().info("Server");
					logger.info("properties:{}", properties.toString());
					
//					conn.set(key.getBytes(), value.getBytes());
//					properties = redisTemplate.getConnectionFactory().getConnection().info("Server");
//					logger.info("properties:{}", properties.toString());
					return null;
				}

			});
			
			logger.info("set key:{}, value:{},  Done!", key, value);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRedisGet(){
		if (redisTemplate == null) {
			logger.error("redisTemplate is null");
			return;
		}
		
		try {
			Object recvMessage = redisTemplate.execute(new RedisCallback<String>() {

				@Override
				public String doInRedis(RedisConnection conn)
						throws DataAccessException {
					// TODO Auto-generated method stub
					byte[] bs = conn.get(key.getBytes());
					bs = conn.get(key.getBytes());
					if (bs != null) {
						return new String(bs);
					}
					return null;
				}
			});
			
			logger.info("redis ret value:{}", recvMessage);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
		
	}
}
