package com.xiws.agentm.http.contract;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

public class HttpRepliesAndCommentsResponse {
	private int rating;
	private String comment;
	private List<HttpCorrespondenceResponse> replies;
	private int userId;
	private XMLGregorianCalendar date;
	private String userName;

	public XMLGregorianCalendar getDate() {
		return date;
	}

	public void setDate(XMLGregorianCalendar date) {
		this.date = date;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<HttpCorrespondenceResponse> getReplies() {
		if (replies == null) {
			replies = new ArrayList<>();
		}
		return replies;
	}

	public void setReplies(List<HttpCorrespondenceResponse> replies) {
		this.replies = replies;
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
