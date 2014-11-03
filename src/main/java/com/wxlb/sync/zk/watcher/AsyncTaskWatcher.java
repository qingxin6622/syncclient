package com.wxlb.sync.zk.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class AsyncTaskWatcher implements Watcher{

	public void process(WatchedEvent event) {
		System.out.println("async:" + event.toString());
	}

}
