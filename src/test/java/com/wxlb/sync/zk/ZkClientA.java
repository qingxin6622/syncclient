package com.wxlb.sync.zk;

import org.apache.log4j.PropertyConfigurator;

import com.wxlb.sync.exception.SyncException;
import com.wxlb.sync.task.test.TestTask;

public class ZkClientA extends ConnectionWatcher{
	
	public static void main(String[] args)throws SyncException{
		PropertyConfigurator.configure(ZkClientA.class.getClassLoader().getResource("log4jA.properties"));
		ZkSyncFactory syncFactory = new ZkSyncFactory("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
		syncFactory.join();
		TestTask task = new TestTask();
		for(int i=0;i<10;i++){
			syncFactory.doSyncTask(task);
		}
	}

}
