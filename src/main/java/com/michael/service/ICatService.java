package com.michael.service;

import java.util.List;

import com.michael.po.Cat;

public interface ICatService {

	List<Cat> all();
	int count();
}
