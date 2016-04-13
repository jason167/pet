package com.michael.zk.zkclient;

import java.util.List;

import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

public interface IZkChildHandler {

	void handler(PathChildrenCacheEvent event, List<ChildData> currentData) throws Exception;
}
