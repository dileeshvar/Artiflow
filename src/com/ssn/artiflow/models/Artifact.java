package com.ssn.artiflow.models;

import java.util.List;

public class Artifact {
	private int artifact_id;
	private String artifact_name;
	private int review_id;
	private int project_id;
	private ArtifactType artifact_type;
	private List<Comments> comments;
	
	public List<Comments> getComments() {
		return comments;
	}
	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	public ArtifactType getArtifact_type() {
		return artifact_type;
	}
	public void setArtifact_type(ArtifactType artifact_type) {
		this.artifact_type = artifact_type;
	}
	public int getArtifact_id() {
		return artifact_id;
	}
	public void setArtifact_id(int artifact_id) {
		this.artifact_id = artifact_id;
	}
	public String getArtifact_name() {
		return artifact_name;
	}
	public void setArtifact_name(String artifact_name) {
		this.artifact_name = artifact_name;
	}
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
}
