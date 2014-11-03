package com.wxlb.sync.node;

public class NodeInfo {
	
	private String nodeId;
	private long joinTime;
	private String host;
	
	public NodeInfo(String nodeId,long joinTime) {
		this.nodeId = nodeId;
		this.joinTime = joinTime;
	}
	
	public String getNodeId() {
		return nodeId;
	}
	public long getJoinTime() {
		return joinTime;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

}
