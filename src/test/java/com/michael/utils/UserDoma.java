package com.michael.utils;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class UserDoma  implements java.io.Serializable{
	/**  */
	private static final long serialVersionUID = 7042532658501852591L;
	String uid;
	int age;
	
	
	final String TYPE = "1";
	boolean lock;
	@JSONField( name = "taddr")
	String addr;
	Date addTime;
	
	public UserDoma() {
		// TODO Auto-generated constructor stub
	}
	
	public UserDoma(String uid, int age, boolean lock, String addr) {
		super();
		this.uid = uid;
		this.age = age;
		this.lock = lock;
		this.addr = addr;
		this.addTime = new Date();
	}


	
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getAddTime() {
		return addTime;
	}

	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public boolean isLock() {
		return lock;
	}
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	
	@JSONField( name = "atype", deserialize = false, serialize = false)
	public String getTYPE() {
		return TYPE;
	}

	@Override
	public String toString() {
		return "UserDoma [uid=" + uid + ", age=" + age + ", TYPE=" + TYPE
				+ ", lock=" + lock + ", addr=" + addr + ", addTime=" + addTime
				+ "]";
	}

	
}
