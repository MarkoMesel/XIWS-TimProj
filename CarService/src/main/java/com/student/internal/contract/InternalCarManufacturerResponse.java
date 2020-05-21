package com.student.internal.contract;

import java.util.List;

public class InternalCarManufacturerResponse extends InternalResponse{

	private List<Manufacturer> manufacturers;

	public void addManufacturer(Manufacturer manufacturer) {
		manufacturers.add(manufacturer);
	}
	
	public List<Manufacturer> getManufacturers() {
		return manufacturers;
	}


	public void setManufacturers(List<Manufacturer> manufacturers) {
		this.manufacturers = manufacturers;
	}


	public static class Manufacturer{
		private int manufacturerId;
		private String manufacturerName;
		public int getManufacturerId() {
			return manufacturerId;
		}
		public void setManufacturerId(int manufacturerId) {
			this.manufacturerId = manufacturerId;
		}
		public String getManufacturerName() {
			return manufacturerName;
		}
		public void setManufacturerName(String manufacturerName) {
			this.manufacturerName = manufacturerName;
		}
	}
}
