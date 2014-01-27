package edu.ssn.sase.artiflow.handleReview;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ssn.sase.artiflow.dal.ArtifactDal;
import edu.ssn.sase.artiflow.dal.ArtifactVersionDal;
import edu.ssn.sase.artiflow.models.Artifact;
import edu.ssn.sase.artiflow.models.ArtifactVersion;
import edu.ssn.sase.artiflow.models.Comments;
import edu.ssn.sase.artiflow.taskmanager.HandleReviewInterface;

public class HandleReviewHandler implements HandleReviewInterface {

	public HandleReviewListOfObjects getReviewArtifactData(int reviewId,
			int artifactId) {
		HandleReviewListOfObjects handleReviewListOfObjects = new HandleReviewListOfObjects();
		ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
		ArrayList<Comments> allComments = new ArrayList<Comments>();
		ArrayList<ArtifactVersion> artifactVersions = new ArrayList<ArtifactVersion>();
		ArtifactDal artifactDal = new ArtifactDal();
		ArtifactVersionDal artifactVersionDal = new ArtifactVersionDal();
		try {
			artifactDal.initiateParams();
			artifactVersionDal.initiateParams();
			artifacts = artifactDal.getArtifactData(artifactId, reviewId);
			artifactVersions = artifactVersionDal.getArtifactData(artifactId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Artifact artifact : artifacts){
			allComments.addAll(artifact.getComments());
		}
		handleReviewListOfObjects.setArtifact(artifacts);
		handleReviewListOfObjects.setartifactVersion(artifactVersions);
		handleReviewListOfObjects.setComments(allComments);
		return handleReviewListOfObjects;
	}

	public HandleReviewListOfObjects updateUploadedArtifact(int reviewId,
			int artifactId, String artifactPath) {
		// TODO Auto-generated method stub
		return null;
	}
}
