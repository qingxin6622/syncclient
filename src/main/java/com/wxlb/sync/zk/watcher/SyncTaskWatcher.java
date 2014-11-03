package com.wxlb.sync.zk.watcher;

import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxlb.sync.exception.SyncRunException;
import com.wxlb.sync.task.Task;
import com.wxlb.sync.util.TaskFactory;
import com.wxlb.sync.util.ObjectDataUtil;

public class SyncTaskWatcher implements Watcher{
	
	private static final Logger logger = LoggerFactory.getLogger(SyncTaskWatcher.class);
	private ZooKeeper zk;
	public SyncTaskWatcher(ZooKeeper zk) {
		this.zk = zk;
	}

	public void process(WatchedEvent event) {
		logger.debug("sync:" + event.toString());
		if(!event.getType().equals(EventType.NodeChildrenChanged)){
			resetWatcher(event.getPath());
			return;
		}
		
		try {
			while(true){
				List<String> taskNodes = zk.getChildren(event.getPath(), false);
				if(taskNodes.size() == 0){
					break;
				}
				for(String node : taskNodes){
					node = event.getPath() + "/" + node;
					System.out.println("child node :"+ node);
					Stat stat = zk.exists(node, false);
					byte[] data = zk.getData(node, false, stat);
					Task task = ObjectDataUtil.bytes2object(data);
					TaskFactory.executeTask(task);
					zk.delete(node, stat.getVersion());
				}
			}
			resetWatcher(event.getPath());
		} catch (Exception e) {
			throw new SyncRunException(e.getMessage(), e);
		}
	}

	private void resetWatcher(String path){
		try {
			zk.getChildren(path, this);
		} catch (Exception e) {
			throw new SyncRunException(e.getMessage(), e);
		}
	}
}
