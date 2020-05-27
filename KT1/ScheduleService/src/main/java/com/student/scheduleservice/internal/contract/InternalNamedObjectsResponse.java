package com.student.scheduleservice.internal.contract;

import java.util.ArrayList;
import java.util.List;

public class InternalNamedObjectsResponse extends InternalResponse {
	private List<NamedObject> objects;
	
	
	public List<NamedObject> getObjects() {
		return objects;
	}

	public void setObjectList(List<NamedObject> objects) {
		this.objects = objects;
	}
	
	public void addObject(NamedObject object) {
		objects.add(object);
	}

	public InternalNamedObjectsResponse() {
		objects = new ArrayList<>();	
	}
	
	public static class NamedObject{
		private String name;
		private int id;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}
}
