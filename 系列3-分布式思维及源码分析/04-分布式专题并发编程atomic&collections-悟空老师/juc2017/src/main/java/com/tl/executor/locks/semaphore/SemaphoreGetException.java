package com.tl.executor.locks.semaphore;

public class SemaphoreGetException extends RuntimeException {

	private static final long serialVersionUID = -4528280099596208630L;

	public SemaphoreGetException() {
		super();
	}

	public SemaphoreGetException(String message) {
		super(message);
	}

	public SemaphoreGetException(String message, Throwable cause) {
		super(message, cause);
	}

	public SemaphoreGetException(Throwable cause) {
		super(cause);
	}

}
