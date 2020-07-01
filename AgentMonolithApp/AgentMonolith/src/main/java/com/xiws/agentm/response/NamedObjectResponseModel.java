package com.xiws.agentm.response;

public class NamedObjectResponseModel {
	private int id;
	private String name;
	
	public NamedObjectResponseModel(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public NamedObjectResponseModel() {}
	
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
