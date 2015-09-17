package com.michael.cache;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class JedisClientTest {

	
	@Test
	public void testJedis(){

        Set<String> sentinels = new HashSet<String>();
        sentinels.add("10.118.242.35:26377");
        sentinels.add("10.118.242.35:36377");        

        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster",
                sentinels, "myredis");

        Jedis jedis = sentinelPool.getResource();

        System.out.println("current Host:"
                + sentinelPool.getCurrentHostMaster());

        String key = "pwd";

        String cacheData = jedis.get(key);

        if (cacheData == null) {
            jedis.del(key);
        }

        jedis.set(key, "123456");// 写入

        System.out.println(jedis.get(key));// 读取

        System.out.println("current Host:"
                + sentinelPool.getCurrentHostMaster());// down掉master，观察slave是否被提升为master

        jedis.set(key, "65321");// 测试新master的写入

        System.out.println(jedis.get(key));// 观察读取是否正常

        sentinelPool.close();
        jedis.close();
	}
}
