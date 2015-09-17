package com.michael.cache;

import org.springframework.data.redis.core.RedisTemplate;

public interface ISpringDataRedisSessionDao {

	RedisTemplate getRedisTemplate();
}
