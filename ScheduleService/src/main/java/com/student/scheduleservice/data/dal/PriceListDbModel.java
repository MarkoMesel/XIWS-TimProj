package com.student.scheduleservice.data.dal;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PriceList")
public class PriceListDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;

	private Integer discountPercentage;
	
	private Integer mileageThreshold;
	
	private Integer mileagePenalty;
	
	private Integer warrantyPrice;
	
	@OneToMany(mappedBy = "priceList", fetch = FetchType.EAGER)
	private List<PriceDbModel> prices;
	
	@OneToMany(mappedBy = "priceList")
	private List<CarPriceListDbModel> cars;
	
	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public int getWarrantyPrice() {
		return warrantyPrice;
	}

	public void setWarrantyPrice(int warrantyPrice) {
		this.warrantyPrice = warrantyPrice;
	}

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

	public int getMileageThreshold() {
		return mileageThreshold;
	}

	public void setMileageThreshold(int mileageThreshold) {
		this.mileageThreshold = mileageThreshold;
	}

	public int getMileagePenalty() {
		return mileagePenalty;
	}

	public void setMileagePenalty(int mileagePenalty) {
		this.mileagePenalty = mileagePenalty;
	}

	public List<PriceDbModel> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceDbModel> prices) {
		this.prices = prices;
	}

	public List<CarPriceListDbModel> getCars() {
		return cars;
	}

	public void setCars(List<CarPriceListDbModel> cars) {
		this.cars = cars;
	}
}
