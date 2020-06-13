package com.student.http.contract;

import java.util.ArrayList;
import java.util.List;

public class HttpCartResponse {

	protected Integer bundleId;
	protected Integer publisherId;
	protected String publisherName;
	protected Integer publisherTypeId;
	protected String publisherTypeName;
	protected List<HttpReservationResponse> cars;

	public Integer getBundleId() {
		return bundleId;
	}

	public void setBundleId(Integer bundleId) {
		this.bundleId = bundleId;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public Integer getPublisherTypeId() {
		return publisherTypeId;
	}

	public void setPublisherTypeId(Integer publisherTypeId) {
		this.publisherTypeId = publisherTypeId;
	}

	public String getPublisherTypeName() {
		return publisherTypeName;
	}

	public void setPublisherTypeName(String publisherTypeName) {
		this.publisherTypeName = publisherTypeName;
	}

	public List<HttpReservationResponse> getCars() {
		if (cars == null) {
			cars = new ArrayList<>();
		}
		return cars;
	}

	public void setCars(List<HttpReservationResponse> cars) {
		this.cars = cars;
	}
}
