package com.xiws.agentm.scheduleservice.data.dal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="Comment")
public class CommentDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int publisherId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PUBLISHER_TYPE_ID")
	private SchedulePublisherTypeDbModel publisherType;
	
	@NotEmpty
	private String comment;
	
	private Boolean approved;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="RESERVATION_ID")
	private ReservationDbModel reservation;
	
	private Long unixTimestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public ReservationDbModel getReservation() {
		return reservation;
	}

	public void setReservation(ReservationDbModel reservation) {
		this.reservation = reservation;
	}

	public Long getUnixTimestamp() {
		return unixTimestamp;
	}

	public void setUnixTimestamp(Long unixTimestamp) {
		this.unixTimestamp = unixTimestamp;
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
}
