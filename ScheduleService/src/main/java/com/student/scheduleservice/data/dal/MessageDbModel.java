package com.student.scheduleservice.data.dal;

import java.math.BigInteger;

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
@Table(name = "Message")
public class MessageDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int publisherId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PUBLISHER_TYPE_ID")
	private PublisherTypeDbModel publisherType;

	@NotEmpty
	private String message;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BUNDLE_ID")
	private BundleDbModel bundle;

	private BigInteger unixTimestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BundleDbModel getBundle() {
		return bundle;
	}

	public void setBundle(BundleDbModel bundle) {
		this.bundle = bundle;
	}

	public BigInteger getUnixTimestamp() {
		return unixTimestamp;
	}

	public void setUnixTimestamp(BigInteger unixTimestamp) {
		this.unixTimestamp = unixTimestamp;
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
}
