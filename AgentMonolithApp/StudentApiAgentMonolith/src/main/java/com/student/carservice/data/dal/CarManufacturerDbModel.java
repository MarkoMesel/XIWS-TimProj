package com.student.carservice.data.dal;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="CarManufacturer")
public class CarManufacturerDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	private String name;
	
	@OneToMany(mappedBy = "manufacturer", fetch=FetchType.EAGER)
	private List<CarModelDbModel> models;

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

	public List<CarModelDbModel> getModels() {
		return models;
	}

	public void setModels(List<CarModelDbModel> models) {
		this.models = models;
	}
}
