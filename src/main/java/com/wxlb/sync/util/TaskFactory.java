package com.wxlb.sync.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.wxlb.sync.task.Task;

public class TaskFactory {
	
	private static ExecutorService threadPools = Executors.newCachedThreadPool();
	
	
	public static void executeTask(Task task) {
		threadPools.execute(task);
	}

}
