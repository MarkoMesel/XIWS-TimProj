package com.xiws.agentm.response;

import java.util.ArrayList;
import java.util.List;

public class PriceListResponseModel {
	private int id;
	private String name;
	private Integer discountPercentage;
	private Integer mileageThreshold;
	private Integer mileagePenalty;
	private Integer warrantyPrice;
	private List<PriceResponseModel> prices;

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

	public List<PriceResponseModel> getPrices() {
		if(prices == null) {
			prices = new ArrayList<>();
		}
		return prices;
	}

	public void setPrices(List<PriceResponseModel> prices) {
		this.prices = prices;
	}
}
