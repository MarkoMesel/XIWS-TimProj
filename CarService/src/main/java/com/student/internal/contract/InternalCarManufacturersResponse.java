package com.student.internal.contract;

import java.util.ArrayList;
import java.util.List;

public class InternalCarManufacturersResponse extends InternalResponse{
	private List<Manufacturer> manufacturers;
	
	public InternalCarManufacturersResponse() {
		super();
		manufacturers = new ArrayList<>();
	}

	public List<Manufacturer> getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(List<Manufacturer> manufacturers) {
		this.manufacturers = manufacturers;
	}
	
	public void addManufacturer(Manufacturer manufacturer) {
		this.manufacturers.add(manufacturer);
	}
	
	public static class Manufacturer {
		private int id;
		private String name;
		
		public Manufacturer(int manufacturerId, String manufacturerName) {
			super();
			this.id = manufacturerId;
			this.name = manufacturerName;
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
}
