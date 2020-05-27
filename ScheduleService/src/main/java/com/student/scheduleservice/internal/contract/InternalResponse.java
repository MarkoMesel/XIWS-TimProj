package com.student.scheduleservice.internal.contract;

public class InternalResponse {
	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public InternalResponse(boolean success) {
		super();
		this.success = success;
	}

	public InternalResponse() {
	}
}
