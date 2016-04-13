package com.michael.zk.zkclient;

import java.security.NoSuchAlgorithmException;

public class ZkClientFactory {

	
	public static Client create(IZkChildHandler childHandler, String url, String path) throws NoSuchAlgorithmException{
		return new ZkClient(childHandler, url, path);
	}
	
}
