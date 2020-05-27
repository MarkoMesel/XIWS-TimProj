package com.student.http.contract;

public class HttpNamedObjectResponse {
	private int id;
	private String name;
	
	public HttpNamedObjectResponse() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
