package com.student.internal.contract;

public class InternalAutherisedResponse extends InternalResponse{
	private boolean autherised;

	public boolean isAutherised() {
		return autherised;
	}

	public void setAutherised(boolean autherised) {
		this.autherised = autherised;
	}

	public InternalAutherisedResponse(boolean autherised) {
		super();
		this.autherised = autherised;
	}

	public InternalAutherisedResponse() {
	}
}
