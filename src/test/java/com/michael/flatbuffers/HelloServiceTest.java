package com.michael.flatbuffers;

import java.nio.ByteBuffer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import com.michael.flatbuffers.command.KV;
import com.michael.flatbuffers.command.Stat;
import com.michael.flatbuffers.command.TestObj;

@ContextConfiguration(locations = { "classpath*:META-INF/application.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class HelloServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private IHelloService helloService;
	
	@Test
	public void testSend(){
		try {
		
			FlatBufferBuilder fbb = new FlatBufferBuilder(1);
			
			int nameOffset = fbb.createString("Michael");
			int listOffset = TestObj.createListVector(fbb, new long[]{0,1,2,3,4,5,6,7,8,9});
			int statOffset = Stat.createStat(fbb, 1002, 1, 1);
			
			TestObj.startTestObj(fbb);
			TestObj.addId(fbb, 1002);
			TestObj.addName(fbb, nameOffset);
			int mon = TestObj.endTestObj(fbb);
			
			nameOffset = fbb.createString("Steven");
			TestObj.startTestObj(fbb);
			TestObj.addId(fbb, 1001);
			TestObj.addName(fbb, nameOffset);
			TestObj.addFlag(fbb, 1);
			TestObj.addList(fbb, listOffset);
			TestObj.addKv(fbb, KV.createKV(fbb, 1, 2));
			TestObj.addStat(fbb, statOffset);
			TestObj.addFromInclude(fbb, 1);
			TestObj.addAnyObj(fbb, mon);
			TestObj.finishTestObjBuffer(fbb, TestObj.endTestObj(fbb));
			TestObj testObj = send(fbb.dataBuffer());
			test(testObj);
			
			logger.info("Done!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
		
	}

	private TestObj send(ByteBuffer dataBuffer) {
		// TODO Auto-generated method stub
		return helloService.send(dataBuffer);
	}

	private void test(TestObj testObj) {
		// TODO Auto-generated method stub
		TestEq(testObj.id(), 1001l);
		TestEq(testObj.name(), "Steven");
		TestEq(testObj.flag(), 1);
		TestEq(testObj.list(0), 0l);
		
		KV kv = testObj.kv();
		TestEq(kv.key(), 1l);
		TestEq(kv.value(), 2d);
		
		TestObj anyObj = (TestObj) testObj.anyObj(new TestObj());
		TestEq(anyObj.id(), 1002l);
		TestEq(anyObj.name(), "Michael");
	}
	
	
	
	static <T> void TestEq(T a, T b) {
        if (!a.equals(b)) {
            System.out.println("" + a.getClass().getName() + " " + b.getClass().getName());
            System.out.println("FlatBuffers test FAILED: \'" + a + "\' != \'" + b + "\'");
            assert false;
            System.exit(1);
        }
    }
}
