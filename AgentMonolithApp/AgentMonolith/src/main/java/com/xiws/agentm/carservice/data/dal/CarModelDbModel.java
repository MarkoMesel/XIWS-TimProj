package com.xiws.agentm.carservice.data.dal;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="CarModel")
public class CarModelDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="MANUFACTURER_ID")
	private CarManufacturerDbModel manufacturer;
	
	@OneToMany(mappedBy = "carModel", fetch=FetchType.EAGER)
	private List<CarDbModel> cars;
	
	@NotEmpty
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CarManufacturerDbModel getCarManufacturer() {
		return manufacturer;
	}

	public void setCarManufacturer(CarManufacturerDbModel manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
