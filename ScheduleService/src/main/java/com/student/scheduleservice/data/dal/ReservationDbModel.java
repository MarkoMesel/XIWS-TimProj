package com.student.scheduleservice.data.dal;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Reservation")
public class ReservationDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Integer rating;
	private int carId;
	private Date startDate;
	private Date endDate;
	private int totalPrice;
	private Integer extraCharges;
	private boolean WarrantyIncluded;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BUNDLE_ID")
	private BundleDbModel bundle;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BundleDbModel getBundle() {
		return bundle;
	}

	public void setBundle(BundleDbModel bundle) {
		this.bundle = bundle;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getExtraCharges() {
		return extraCharges;
	}

	public void setExtraCharges(Integer extraCharges) {
		this.extraCharges = extraCharges;
	}

	public boolean isWarrantyIncluded() {
		return WarrantyIncluded;
	}

	public void setWarrantyIncluded(boolean warrantyIncluded) {
		WarrantyIncluded = warrantyIncluded;
	}
}
