package edu.ssn.sase.artiflow.handleReview;

import java.util.ArrayList;

import edu.ssn.sase.artiflow.models.Artifact;
import edu.ssn.sase.artiflow.models.ArtifactVersion;
import edu.ssn.sase.artiflow.models.Comments;

public class HandleReviewListOfObjects {
	private ArrayList<Artifact> artifact;
	private ArrayList<ArtifactVersion> artifactVersion;
	private ArrayList<Comments> comments;
	
	public ArrayList<Comments> getComments() {
		return comments;
	}
	public void setComments(ArrayList<Comments> comments) {
		this.comments = comments;
	}
	public ArrayList<Artifact> getArtifact() {
		return artifact;
	}
	public void setArtifact(ArrayList<Artifact> artifact) {
		this.artifact = artifact;
	}
	public ArrayList<ArtifactVersion> getArtifactVersion() {
		return artifactVersion;
	}
	public void setartifactVersion(ArrayList<ArtifactVersion> artifactVersion) {
		this.artifactVersion = artifactVersion;
	}
}
