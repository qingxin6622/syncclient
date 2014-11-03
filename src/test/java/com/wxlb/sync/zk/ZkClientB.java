package com.wxlb.sync.zk;

import org.apache.log4j.PropertyConfigurator;

import com.wxlb.sync.exception.SyncException;
import com.wxlb.sync.task.test.TestTask;

public class ZkClientB extends ConnectionWatcher{
	
	public static void main(String[] args)throws SyncException{
		PropertyConfigurator.configure(ZkClientA.class.getClassLoader().getResource("log4jB.properties"));
		ZkSyncFactory syncFactory = new ZkSyncFactory("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
		syncFactory.join();
		TestTask task = new TestTask();
		syncFactory.doSyncTask(task);
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
