package com.michael.cache.impl;

import org.springframework.data.redis.core.RedisTemplate;

import com.michael.cache.ISpringDataRedisSessionDao;


public class SpringDataRedisSessionDao implements ISpringDataRedisSessionDao{

	private RedisTemplate redisTemplate;
	
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}
	
}
