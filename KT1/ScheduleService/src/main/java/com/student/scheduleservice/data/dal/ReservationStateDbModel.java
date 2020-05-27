package com.student.scheduleservice.data.dal;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="ReservationState")
public class ReservationStateDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	private String name;
	
	@OneToMany(mappedBy = "state")
	private List<BundleDbModel> bundles;

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

	public List<BundleDbModel> getBundles() {
		return bundles;
	}

	public void setBundles(List<BundleDbModel> bundles) {
		this.bundles = bundles;
	}
}
