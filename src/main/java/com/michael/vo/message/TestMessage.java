package com.michael.vo.message;

import java.util.Date;

public class TestMessage implements Message {

	public String now;
	
	public TestMessage() {
		// TODO Auto-generated constructor stub
		now = new Date().toLocaleString();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return now;
	}
}
