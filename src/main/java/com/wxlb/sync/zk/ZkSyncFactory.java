package com.wxlb.sync.zk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.AsyncCallback.Children2Callback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Transaction;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxlb.sync.SyncFactory;
import com.wxlb.sync.exception.SyncException;
import com.wxlb.sync.exception.SyncRunException;
import com.wxlb.sync.node.NodeInfo;
import com.wxlb.sync.task.Task;
import com.wxlb.sync.util.ObjectDataUtil;
import com.wxlb.sync.zk.watcher.AsyncTaskWatcher;
import com.wxlb.sync.zk.watcher.SyncTaskWatcher;

public class ZkSyncFactory extends ConnectionWatcher implements SyncFactory{
	private static final Logger logger = LoggerFactory.getLogger(ZkSyncFactory.class);
	private NodeInfo currNodeInfo;
	private String basePath = "/wxlb/sync";
	private String clusterNode = basePath + "/cluster";
	private String syncTaskNode = basePath + "/tasks/sync";
	private String asyncTaskNode = basePath + "/tasks/async";
	private String nodePerfix = "node_";
	
	
	public ZkSyncFactory(String host){
		try {
			connect(host);
		} catch (Exception e) {
			throw new SyncRunException(e.getMessage(),e);
		}
	}
	
	public NodeInfo join() throws SyncException{
		String path = clusterNode + "/" + nodePerfix;
		try {
			String realPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			String nodeId = realPath.substring(realPath.lastIndexOf(nodePerfix) + nodePerfix.length());
			currNodeInfo = new NodeInfo(nodeId,System.currentTimeMillis());
			String syncNode = syncTaskNode + "/" + nodeId;
			zk.create(syncNode, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			String asyncNode = asyncTaskNode + "/" + nodeId;
			zk.create(asyncNode, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			
			zk.getChildren(syncNode, new SyncTaskWatcher(zk), new Children2Callback() {
				
				public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
					System.out.println("current sync task size:" + children.size());
				}
			}, null);
			
			zk.getChildren(asyncNode, new AsyncTaskWatcher(), new Children2Callback() {
				
				public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
					System.out.println("current async task size:" + children.size());
				}
			}, null);
			
			logger.info("join cluster success,node id is:{}",nodeId);
			return currNodeInfo;
		} catch (Exception e) {
			throw new SyncException(e.getMessage(), e);
		}
	}

	public void doSyncTask(Task task) {
		try {
			Transaction transaction = zk.transaction();
			List<NodeInfo> nodes = getNodes(false);
			byte[] data = ObjectDataUtil.object2bytes(task);
			for(NodeInfo node : nodes){
				String path = syncTaskNode + "/" + node.getNodeId() + "/task_";
				transaction.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
			}
			transaction.commit();
		} catch (Exception e) {
			throw new SyncRunException(e.getMessage(), e);
		}
	}

	public void doSyncTask(Task task, NodeInfo[] nodes) {

	}

	public List<Object> doAsyncTask(Task task) {
		return Collections.emptyList();
	}

	public List<Object> doAsyncTask(Task task, NodeInfo[] nodes) {
		return Collections.emptyList();
	}

	public Lock getLock() {
		return null;
	}

	public NodeInfo getNodeInfo(String nodeId) {
		return null;
	}

	public List<NodeInfo> getNodes(boolean includeLocal) {
		List<NodeInfo> nodes = new ArrayList<NodeInfo>();
		try {
			List<String> paths = zk.getChildren(clusterNode, false);
			for(String path : paths){
				String nodeId = path.substring(path.lastIndexOf(nodePerfix) + nodePerfix.length());
				if(nodeId.equals(currNodeInfo.getNodeId())){
					if(includeLocal){
						nodes.add(currNodeInfo);
					}
				}else{
					NodeInfo nodeInfo = new NodeInfo(nodeId, 0L);
					nodes.add(nodeInfo);
				}
			}
		} catch (Exception e) {
			throw new SyncRunException(e.getMessage(), e);
		}
		return nodes;
	}
}
