package com.wxlb.sync.exception;

public class SyncRunException extends RuntimeException{

	private static final long serialVersionUID = -6990631303083328810L;
	
	public SyncRunException(String message, Throwable cause) {
		super(message,cause);
	}

}
