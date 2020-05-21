package com.student.internal.contract;

public class InternalNamedObjectResponse extends InternalResponse {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
