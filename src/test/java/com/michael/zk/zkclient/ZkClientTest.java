package com.michael.zk.zkclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkClientTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private String url= "10.118.242.35:2181";
	private String path = "/fls";
	private Client client;

	@Before
	public void init(){
		try {
			client = ZkClientFactory.create(new IZkChildHandler() {
				
				@Override
				public void handler(PathChildrenCacheEvent event, List<ChildData> currentData) throws Exception {
					// TODO Auto-generated method stub
					try {
						StringBuilder buff = new StringBuilder();
						for (ChildData childData : currentData) {
							buff.append(new String(childData.getPath()))
							.append("=>")
							.append(new String(childData.getData() == null ? new byte[1] : childData.getData()))
							.append(";");
						}
						logger.info("path child cache event:{}, data:{}", event.getType(), buff.toString());
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			},
			url, path);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void zkClientTest(){
		try {
			client.start();
			while(true){
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String command = reader.readLine();
				if (command.equals("quit")) {
					break;
				}
				
				String[] strings = command.split(",");
				if (strings.length != 2) {
					logger.info("command format is:'path,data'");
					continue;
				}
				String path = strings[0];
				String data = strings[1];
				client.put(path, data);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After
	public void destroy(){
		if (client != null) {
			client.stop();
		}
		
		client = null;
		logger.info("Done!");
	}
	
}
