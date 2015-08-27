package com.michael.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.michael.po.Cat;
import com.michael.service.ICatService;

public class CatServiceImpl implements ICatService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<Cat> all() {
		// TODO Auto-generated method stub
		ArrayList<Cat> catList = Lists.newArrayList();
		catList.add(new Cat("xmm 1"));
		catList.add(new Cat("xmm 2"));
		logger.info("catList:{}", catList);
		return catList;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		logger.info("count:2");
		return 2;
	}

}
