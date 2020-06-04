package com.student.http.contract;

import java.util.List;

public class HttpRepliesAndCommentsResponse {

	private List<HttpCommentResponse> comments;

	public List<HttpCommentResponse> getComments() {
		return comments;
	}

	public void setComments(List<HttpCommentResponse> comments) {
		this.comments = comments;
	}
}
