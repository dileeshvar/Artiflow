package com.ssn.artiflow.models;

public class Reviewer {
	private int reviewer_id;
	private int review_id;
	private boolean is_optional;
	private int user_id;
	private String ReviewerName;
	
	public String getReviewerName() {
		return ReviewerName;
	}
	public void setReviewerName(String reviewerName) {
		ReviewerName = reviewerName;
	}
	public boolean isIs_optional() {
		return is_optional;
	}
	public int getReviewer_id() {
		return reviewer_id;
	}
	public void setReviewer_id(int reviewer_id) {
		this.reviewer_id = reviewer_id;
	}
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public boolean is_optional() {
		return is_optional;
	}
	public void setIs_optional(boolean is_optional) {
		this.is_optional = is_optional;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
