package com.wxlb.sync.redis;

import java.util.List;
import java.util.concurrent.locks.Lock;

import com.wxlb.sync.SyncFactory;
import com.wxlb.sync.exception.SyncException;
import com.wxlb.sync.node.NodeInfo;
import com.wxlb.sync.task.Task;

public class RedisSyncFactory implements SyncFactory{

	public NodeInfo join() throws SyncException {
		return null;
	}

	public void doSyncTask(Task task) {
		
	}

	public void doSyncTask(Task task, NodeInfo[] nodes) {
		
	}

	public List<Object> doAsyncTask(Task task) {
		return null;
	}

	public List<Object> doAsyncTask(Task task, NodeInfo[] nodes) {
		return null;
	}

	public Lock getLock() {
		return null;
	}

	public NodeInfo getNodeInfo(String nodeId) {
		return null;
	}

	public List<NodeInfo> getNodes(boolean includeLocal) {
		return null;
	}

}
