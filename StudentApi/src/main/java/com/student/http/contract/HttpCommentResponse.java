package com.student.http.contract;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.student.soap.scheduleservice.contract.SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply;

public class HttpCommentResponse {

	private int rating;
	private String comment;
	private List<Reply> replies;
	private int userId;
	private String userName;
	private XMLGregorianCalendar date;

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

	public XMLGregorianCalendar getDate() {
		return date;
	}

	public void setDate(XMLGregorianCalendar date) {
		this.date = date;
	}

	public List<Reply> getReplies() {
		return replies;
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

	public void setReplies(List<Reply> list) {
		this.replies = list;
	}
}
