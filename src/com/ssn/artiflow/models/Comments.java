package com.ssn.artiflow.models;

public class Comments {
	private int commentsId;
	private String commentValue;
	private int reviewId;
	private int userId;
	private int artifactVersionId;
	public int getCommentsId() {
		return commentsId;
	}
	public void setCommentsId(int commentsId) {
		this.commentsId = commentsId;
	}
	public String getCommentValue() {
		return commentValue;
	}
	public void setCommentValue(String commentValue) {
		this.commentValue = commentValue;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getArtifactVersionId() {
		return artifactVersionId;
	}
	public void setArtifactVersionId(int artifactVersionId) {
		this.artifactVersionId = artifactVersionId;
	}
}
