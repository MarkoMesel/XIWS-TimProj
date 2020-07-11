package com.xiws.agentm.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

//import com.student.http.contract.HttpCorrespondenceResponse;

public class RepliesAndCommentsResponseModel {
	private int rating;
	private String comment;
	private List<CorrespondenceResponseModel> replies;
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

	public List<CorrespondenceResponseModel> getReplies() {
		if (replies == null) {
			replies = new ArrayList<>();
		}
		return replies;
	}

	public void setReplies(List<CorrespondenceResponseModel> replies) {
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
