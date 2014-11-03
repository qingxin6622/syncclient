package com.wxlb.sync.task;

import java.io.Externalizable;

public interface Task extends Runnable, Externalizable {

	public Object getResult();

}
