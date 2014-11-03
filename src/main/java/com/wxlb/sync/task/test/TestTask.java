package com.wxlb.sync.task.test;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxlb.sync.task.Task;

public class TestTask implements Task {

	private static final Logger logger = LoggerFactory.getLogger(TestTask.class);
	
	public void run() {
		logger.info("this is test task.");
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		
	}

	public Object getResult() {
		return null;
	}

}
