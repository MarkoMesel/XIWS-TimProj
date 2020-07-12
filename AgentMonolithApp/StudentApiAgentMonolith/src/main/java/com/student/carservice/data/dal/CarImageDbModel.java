package com.student.carservice.data.dal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CarImage")
public class CarImageDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CAR_ID")
	private CarDbModel car;
	
	@Lob
	private byte[] image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CarDbModel getCar() {
		return car;
	}

	public void setCar(CarDbModel car) {
		this.car = car;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] media) {
		this.image = media;
	}
}
