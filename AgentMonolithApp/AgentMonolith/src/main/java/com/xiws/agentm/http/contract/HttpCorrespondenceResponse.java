package com.xiws.agentm.http.contract;

import javax.xml.datatype.XMLGregorianCalendar;

public class HttpCorrespondenceResponse {
	private int id;
    private int publisherId;
    private int publisherTypeId;
    private String comment;
    private String publisherName;
    private String publisherTypeName;
    private XMLGregorianCalendar date;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	public int getPublisherTypeId() {
		return publisherTypeId;
	}
	public void setPublisherTypeId(int publisherTypeId) {
		this.publisherTypeId = publisherTypeId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getPublisherTypeName() {
		return publisherTypeName;
	}
	public void setPublisherTypeName(String publisherTypeName) {
		this.publisherTypeName = publisherTypeName;
	}
	public XMLGregorianCalendar getDate() {
		return date;
	}
	public void setDate(XMLGregorianCalendar date) {
		this.date = date;
	}
}
