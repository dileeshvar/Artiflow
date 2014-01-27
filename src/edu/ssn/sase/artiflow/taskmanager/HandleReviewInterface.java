package edu.ssn.sase.artiflow.taskmanager;

import edu.ssn.sase.artiflow.handleReview.HandleReviewListOfObjects;

public interface HandleReviewInterface {
	public HandleReviewListOfObjects getReviewArtifactData(int reviewId, int artifactId); 
	
	public HandleReviewListOfObjects updateUploadedArtifact(int reviewId, int artifactId, String artifactPath);
}
