package com.wxlb.sync;

import java.util.List;
import java.util.concurrent.locks.Lock;

import com.wxlb.sync.exception.SyncException;
import com.wxlb.sync.node.NodeInfo;
import com.wxlb.sync.task.Task;

public interface SyncFactory {
	
	public NodeInfo join() throws SyncException;
	
	public void doSyncTask(Task task);
	
	public void doSyncTask(Task task,NodeInfo[] nodes);
	
	public List<Object> doAsyncTask(Task task);
	
	
	public List<Object> doAsyncTask(Task task,NodeInfo[] nodes);
	
	public Lock getLock();
	
	public NodeInfo getNodeInfo(String nodeId);
	
	public List<NodeInfo> getNodes(boolean includeLocal);
}
