package com.michael.ketama;

import java.util.ArrayList;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class KetamaTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void testKetama(){
		try {
			KetamaImpl ketama = new KetamaImpl(16);
			ArrayList<TcpSession> list = Lists.newArrayList(
						new TcpSession("127.0.0.2:1234"), 
						new TcpSession("127.0.0.3:1234")
					);
			ketama.updateSessions(list, HashAlgorithm.KETAMA_HASH);
			
			TcpSession session = ketama.getSessionByKey("uid");
			logger.info("key uid:{}", session);
			logger.info("Done!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
	}
}
