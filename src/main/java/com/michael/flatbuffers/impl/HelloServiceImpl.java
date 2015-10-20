package com.michael.flatbuffers.impl;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.michael.flatbuffers.IHelloService;
import com.michael.flatbuffers.command.TestObj;

@Service("helloService")
public class HelloServiceImpl implements IHelloService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public TestObj send(ByteBuffer _bb) {
		// TODO Auto-generated method stub
		LOG(_bb);
		return TestObj.getRootAsTestObj(_bb);
	}

	private void LOG(ByteBuffer _bb) {
		// TODO Auto-generated method stub
		byte[] array = _bb.array();
		StringBuffer stringBuffer = new StringBuffer();
		for (byte b : array) {
			String hexString = Integer.toHexString(b);
			if (hexString.length() <= 1) {
				hexString = "0" + hexString;
			}
			stringBuffer.append("0x"+hexString+" ");
		}
		logger.info("FlatBuffers hex string : \n{}\n", stringBuffer.toString());
	}

}
