package com.xiws.agentm.response;

public class CarPriceResponseModel {
    private int totalPrice;
    private Integer discount;
    private int price;
    private Integer mileagePenalty;
    private Integer mileageThreshold;
    private Integer collisionWarranty;
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Integer getMileagePenalty() {
		return mileagePenalty;
	}
	public void setMileagePenalty(Integer mileagePenalty) {
		this.mileagePenalty = mileagePenalty;
	}
	public Integer getMileageThreshold() {
		return mileageThreshold;
	}
	public void setMileageThreshold(Integer mileageThreshold) {
		this.mileageThreshold = mileageThreshold;
	}
	public Integer getCollisionWarranty() {
		return collisionWarranty;
	}
	public void setCollisionWarranty(Integer collisionWarranty) {
		this.collisionWarranty = collisionWarranty;
	}
}
