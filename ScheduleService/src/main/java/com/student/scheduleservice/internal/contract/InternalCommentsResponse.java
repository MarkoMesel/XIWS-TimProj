package com.student.scheduleservice.internal.contract;

import java.util.ArrayList;
import java.util.List;

public class InternalCommentsResponse extends InternalResponse{
	
	private List<Comment> comments;
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public InternalCommentsResponse() {
		comments = new ArrayList<>();
	}
	
	public static class Comment{
		private String comment;
		private int rating;
		private String reply;
		
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public int getRating() {
			return rating;
		}
		public void setRating(int rating) {
			this.rating = rating;
		}
		public String getReply() {
			return reply;
		}
		public void setReply(String reply) {
			this.reply = reply;
		}
	}
}
