package com.michael.proxy.service.impl;

import com.michael.proxy.service.IHello;
import com.michael.proxy.service.IWorld;

public class Hello implements IHello,IWorld {

	@Override
	public String say(String arg) {
		// TODO Auto-generated method stub
		return "Hello " + arg;
	}

	@Override
	public String hi(String arg) {
		// TODO Auto-generated method stub
		return "Hi " + arg;
	}


}
