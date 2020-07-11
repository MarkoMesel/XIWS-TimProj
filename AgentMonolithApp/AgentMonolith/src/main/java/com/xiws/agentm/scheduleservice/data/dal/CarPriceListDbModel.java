package com.xiws.agentm.scheduleservice.data.dal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CarPriceList")
public class CarPriceListDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int carId;

	private Long unixTimestamp;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRICE_LIST_ID")
	private PriceListDbModel priceList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public Long getUnixTimestamp() {
		return unixTimestamp;
	}

	public void setUnixTimestamp(Long unixTimestamp) {
		this.unixTimestamp = unixTimestamp;
	}

	public PriceListDbModel getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceListDbModel priceList) {
		this.priceList = priceList;
	}
}
