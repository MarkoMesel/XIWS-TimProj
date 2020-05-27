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
@Table(name = "Bundle")
public class BundleDbModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int userId;

	private int publisherId;
	
	private int totalPrice;

	private Integer extraCharges;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PUBLISHER_TYPE_ID")
	private PublisherTypeDbModel publisherType;

	@OneToMany(mappedBy = "bundle")
	private List<ReservationDbModel> reservations;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATE_ID")
	private ReservationStateDbModel state;

	@OneToMany(mappedBy = "reservation")
	private List<MessageDbModel> messages;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public List<ReservationDbModel> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationDbModel> reservations) {
		this.reservations = reservations;
	}

	public ReservationStateDbModel getState() {
		return state;
	}

	public void setState(ReservationStateDbModel state) {
		this.state = state;
	}

	public List<MessageDbModel> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDbModel> messages) {
		this.messages = messages;
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
}
