package com.michael.ketama;

public class TcpSession {
	private String sockStr;
	private int weight = 1;
	
	public TcpSession(String sockStr, int weight) {
		super();
		this.sockStr = sockStr;
		this.weight = weight;
	}
	
	public TcpSession(String sockStr) {
		// TODO Auto-generated constructor stub
		this.sockStr = sockStr;
	}
	public String getSockStr(){
		return sockStr;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return "TcpSession [sockStr=" + sockStr + ", weight=" + weight + "]";
	}
	

}
