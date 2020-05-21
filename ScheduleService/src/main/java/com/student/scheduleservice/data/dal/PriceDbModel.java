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
@Table(name="Price")
public class PriceDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Date date;
	
	private int price;
	
	private int publisherId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PUBLISHER_TYPE_ID")
	private PublisherTypeDbModel publisherType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRICE_LIST_ID")
	private PriceListDbModel priceList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public PriceListDbModel getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceListDbModel priceList) {
		this.priceList = priceList;
	}
}
