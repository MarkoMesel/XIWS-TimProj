package com.xiws.agentm.http.contract;

public class HttpAddPriceListRequest {

	private int discountPercentage;
	private int mileageThreshold;
	private int mileagePenalty;
	private String name;
	private int warrantyPrice;

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWarrantyPrice() {
		return warrantyPrice;
	}

	public void setWarrantyPrice(int warrantyPrice) {
		this.warrantyPrice = warrantyPrice;
	}

}
