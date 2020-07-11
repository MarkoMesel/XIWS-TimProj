package com.xiws.agentm.response;

import java.util.ArrayList;
import java.util.List;

public class BundleResponseModel {

	private Integer bundleId;
	private Integer publisherId;
	private String publisherName;
	private Integer publisherTypeId;
	private String publisherTypeName;
	private int stateId;
	private String stateName;
	private int userId;
	private String userName;
	private List<ReservationResponseModel> cars;

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

	public List<ReservationResponseModel> getCars() {
		if (cars == null) {
			cars = new ArrayList<>();
		}
		return cars;
	}

	public void setCars(List<ReservationResponseModel> cars) {
		this.cars = cars;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
