package com.xiws.agentm.request;

public class AddMessageRequestModel {
	private int bundleId;
	private String message;

	public int getBundleId() {
		return bundleId;
	}

	public void setBundleId(int bundleId) {
		this.bundleId = bundleId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
