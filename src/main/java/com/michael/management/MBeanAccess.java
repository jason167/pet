package com.michael.management;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class MBeanAccess {

	public static void main(String[] args) throws Exception {
		// 打印GC次数
//		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//		ObjectName youngMBean = new ObjectName("java.lang:type=GarbageCollector,name=MarkSweepCompact");
//		ObjectName tenuredMBean = new ObjectName("java.lang:type=GarbageCollector,name=Copy");
//		System.out.println("YGC:" + mbs.getAttribute(youngMBean, "CollectionCount"));
//		System.out.println("FGC:" + mbs.getAttribute(tenuredMBean, "CollectionCount"));
//		System.gc();
//		System.out.println("YGC:" + mbs.getAttribute(youngMBean, "CollectionCount"));
//		System.out.println("FGC:" + mbs.getAttribute(tenuredMBean, "CollectionCount"));
		int pid = getPid();
		System.out.println("pid:" + pid);
		// System.in.read(); // block the program so that we can do some probing
		// on it
		
		System.in.read();
		System.out.println("Done!");
	}

	private static int getPid() {
		// 获取进程的PID
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName(); // format:"pid@hostname"
		try {
			System.out.println("runtime name:"+ name);
			return Integer.parseInt(name.substring(0, name.indexOf('@')));
		} catch (Exception e) {
			return -1;
		}
	}
}
