package com.michael.zk.zkclient;

import org.apache.curator.framework.recipes.cache.ChildData;

public interface Client {

	void start() throws Exception;
	void stop();
	ChildData get(String path);
	void put(String path, String data) throws Exception;
	
}
