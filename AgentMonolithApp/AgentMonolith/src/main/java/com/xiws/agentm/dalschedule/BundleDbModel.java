package com.xiws.agentm.dalschedule;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PUBLISHER_TYPE_ID")
	private SchedulePublisherTypeDbModel publisherType;

	@OneToMany(mappedBy = "bundle", cascade = CascadeType.REFRESH, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ReservationDbModel> reservations;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATE_ID")
	private ReservationStateDbModel state;

	@OneToMany(mappedBy = "bundle")
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

	public SchedulePublisherTypeDbModel getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(SchedulePublisherTypeDbModel publisherType) {
		this.publisherType = publisherType;
	}

	public List<ReservationDbModel> getReservations() {
		if(reservations == null) {
			reservations = new ArrayList<>();
		}
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
}
