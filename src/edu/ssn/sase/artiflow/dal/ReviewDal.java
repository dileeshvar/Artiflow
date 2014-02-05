package edu.ssn.sase.artiflow.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import edu.ssn.sase.artiflow.models.Artifact;
import edu.ssn.sase.artiflow.models.Review;
import edu.ssn.sase.artiflow.models.Reviewer;
import edu.ssn.sase.artiflow.utils.ConnectDB;

public class ReviewDal {
	Connection DBConn = null;
	PreparedStatement statement = null;

	public void initiateParams(String SQLServerIP, String databaseName)
			throws SQLException {
		DBConn = ConnectDB.getConnection(SQLServerIP, databaseName);
	}
	
	public void initiateParams()
			throws SQLException {
		DBConn = ConnectDB.getConnection("localhost", "artiflow");
	}

	public ResultSet getProjectFromProjName(String project_name)
			throws SQLException {
		statement = DBConn
				.prepareStatement("select * from project where project_name=?");
		statement.setString(1, project_name);
		ResultSet rs = statement.executeQuery();
		return rs;
	}

	public ResultSet getReviewCount() throws SQLException {
		statement = DBConn.prepareStatement("select count(*) from review");
		ResultSet rs = statement.executeQuery();
		return rs;
	}

	public ResultSet getProjNameFromProjId(int projId) throws SQLException {
		statement = DBConn
				.prepareStatement("select * from project where project_id=?");
		statement.setInt(1, projId);
		ResultSet rs = statement.executeQuery();
		return rs;
	}
	
	public ResultSet getReviewsInitiatedByUser(int userId) throws SQLException {
		statement = DBConn
				.prepareStatement("select * from review where author_id=?");
		statement.setInt(1, userId);
		ResultSet rs = statement.executeQuery();
		return rs;
	}
	
	public ResultSet getReviewsToBeReviewedByUser(int userId) throws SQLException {
		statement = DBConn.prepareStatement("select review.* from review"
				+ " inner join reviewers on reviewers.review_id = review.review_id where reviewers.user_id = ?");
		statement.setInt(1, userId);
		ResultSet rs = statement.executeQuery();
		return rs;
	}

	public ResultSet getReviewerIdFromReview(Review review) throws SQLException {
		statement = DBConn
				.prepareStatement("select user_id from reviewers where review_id = ?");
		statement.setInt(1, review.getReview_id());
		ResultSet rs = statement.executeQuery();
		return rs;
	}

	public int updateReview(Review review, Timestamp ts) throws SQLException {
		statement = DBConn
				.prepareStatement("insert into review (review_id,story_name,objective,project_id,author_id,date_created,status_id) values (?,?,?,?,?,?,?)");
		statement.setInt(1, review.getReview_id());
		statement.setString(2, review.getStoryName());
		statement.setString(3, review.getObjective());
		statement.setInt(4, review.getProject_id());
		statement.setInt(5, 1);
		statement.setTimestamp(6, ts);
		statement.setInt(7, review.getStatus_id());
		return statement.executeUpdate();
	}

	public int updateArtifacts(Artifact arti, Timestamp ts) throws SQLException{
		int artifactExecute = 0;
		int artifactmapID = getArtifactMapId();
		statement = DBConn
				.prepareStatement("insert into artifact (artifact_id,artifact_name,review_id,Project_id,artifact_type_id, date_created, artifact_map_id, is_current) values (?,?,?,?,?,?,?,?)");
		statement.setInt(1, arti.getArtifact_id());
		statement.setString(2, arti.getArtifact_name());
		statement.setInt(3, arti.getReview_id());
		statement.setInt(4, arti.getProject_id());
		statement.setInt(5, arti.getArtifact_type().getArtifactTypeId());
		statement.setTimestamp(6, ts);
		statement.setInt(7,artifactmapID);
		statement.setBoolean(8, true);
		artifactExecute =  statement.executeUpdate();
		insertArtifactVersion(arti.getArtifact_id());		
		return artifactExecute;
	}

	public int updateReviewers(Reviewer rev1) throws SQLException {
		statement = DBConn
				.prepareStatement("insert into reviewers (reviewers_id,review_id,is_optional,user_id) values (?,?,?,?)");
		statement.setInt(1, rev1.getReviewer_id());
		statement.setInt(2, rev1.getReview_id());
		statement.setBoolean(3, rev1.is_optional());
		statement.setInt(4, rev1.getUser_id());
		return statement.executeUpdate();
	}
	
	public int getArtifactVersion(int artifactId) throws SQLException {
		statement = DBConn.prepareStatement("select count(*) from artifact_version where artifact_id = ?");
		statement.setInt(1, artifactId);
		ResultSet rs = statement.executeQuery();
		return rs.next() ? rs.getInt(1): 0;
	}
	
	public int insertArtifactVersion(int artifactId) throws SQLException{
		int versionNo = getArtifactVersion(artifactId);
		statement = DBConn
				.prepareStatement("insert into artifact_version (artifact_id,version_no) values (?,?)");
		statement.setInt(1, artifactId);
		statement.setInt(2, versionNo+1);
		return statement.executeUpdate();
	}
	
	public int getArtifactMapId() throws SQLException {
		statement = DBConn.prepareStatement("select max(artifact_map_id) from artifact");
		ResultSet rs = statement.executeQuery();
		return (rs.next() ? rs.getInt(1): 0)+1;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		DBConn.close();
	}
	
	public ArrayList<Review> getReviewList(int user_id, boolean isAuthor){
		ArrayList<Review> reviews =  new ArrayList<Review>();
		ArtifactDal artifactDal = new ArtifactDal();
		try {
			artifactDal.initiateParams();
			if(isAuthor)
				statement = DBConn.prepareStatement("select * from review where author_id ="+user_id+" ;");
			else{
				statement = DBConn.prepareStatement("select * from review where review_id in (SELECT review_id FROM artiflow.reviewers where user_id = "+user_id+" );");
			}
				ResultSet rs = statement.executeQuery();
				while(rs.next()){
					Review review = new Review();
					review.setReview_id(rs.getInt("review_id"));
					review.setAuthor_id(rs.getInt("author_id"));
					review.setObjective(rs.getString("objective"));
					review.setProject_id(rs.getInt("project_id"));
					review.setStoryName(rs.getString("story_name"));
					review.setStatus_id(rs.getInt("status_id"));
					review.setArtifacts(artifactDal.getArtifactData(rs.getInt("review_id")));
					reviews.add(review);
				}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reviews;
	}
}
