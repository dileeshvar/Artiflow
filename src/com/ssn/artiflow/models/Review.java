package com.ssn.artiflow.models;

import java.util.List;

public class Review {
	private int review_id;
	private String storyName;
	private String objective;
	private int project_id;
	private int author_id;
	private int status_id;
	private List<Reviewer> reviewers;
	private List<Artifact> artifacts;
	
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public String getStoryName() {
		return storyName;
	}
	public void setStoryName(String storyName) {
		this.storyName = storyName;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public int getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public List<Reviewer> getReviewers() {
		return reviewers;
	}
	public void setReviewers(List<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}
	public List<Artifact> getArtifacts() {
		return artifacts;
	}
	public void setArtifacts(List<Artifact> artifacts) {
		this.artifacts = artifacts;
	}
	
}
