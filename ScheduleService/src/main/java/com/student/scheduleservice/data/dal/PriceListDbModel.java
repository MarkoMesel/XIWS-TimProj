package com.student.scheduleservice.data.dal;

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
	
	private int publisherId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PUBLISHER_TYPE_ID")
	private PublisherTypeDbModel publisherType;
	
	@OneToMany(mappedBy = "priceList", fetch = FetchType.EAGER)
	private List<PriceDbModel> prices;
	
	@OneToMany(mappedBy = "priceList")
	private List<CarPriceListDbModel> cars;

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

	public Integer getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Integer discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Integer getMileageThreshold() {
		return mileageThreshold;
	}

	public void setMileageThreshold(Integer mileageThreshold) {
		this.mileageThreshold = mileageThreshold;
	}

	public Integer getMileagePenalty() {
		return mileagePenalty;
	}

	public void setMileagePenalty(Integer mileagePenalty) {
		this.mileagePenalty = mileagePenalty;
	}

	public Integer getWarrantyPrice() {
		return warrantyPrice;
	}

	public void setWarrantyPrice(Integer warrantyPrice) {
		this.warrantyPrice = warrantyPrice;
	}

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public PublisherTypeDbModel getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(PublisherTypeDbModel publisherType) {
		this.publisherType = publisherType;
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
