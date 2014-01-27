package edu.ssn.sase.artiflow.dal;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.ssn.sase.artiflow.models.Artifact;
import edu.ssn.sase.artiflow.models.ArtifactType;
import edu.ssn.sase.artiflow.models.Comments;
import edu.ssn.sase.artiflow.utils.ConnectDB;

public class ArtifactDal {

	Connection DBConn = null;
	Statement statement = null;

	public void initiateParams(String SQLServerIP, String databaseName)
			throws SQLException {
		DBConn = ConnectDB.getConnection(SQLServerIP, databaseName);
		statement = DBConn.createStatement();
	}
	
	public void initiateParams()
			throws SQLException {
		DBConn = ConnectDB.getConnection("localhost", "artiflow");
		statement = DBConn.createStatement();
	}

	public ResultSet getArtifactType(String artTypeName) throws SQLException {
		ResultSet rs = statement
				.executeQuery("select artifact_type_id from artifact_type where artifact_type_name='"
						+ artTypeName + "'");
		return rs;
	}

	public ResultSet getCurrentArtifactCount() throws SQLException {
		ResultSet rs = statement.executeQuery("select count(*) from artifact");
		return rs;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		DBConn.close();
	}
	
	public ArrayList<Artifact> getArtifactData(int artifactId, int reviewId){
		ArrayList<Comments> comments = null;
		ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
		ArtifactType artifactType = new ArtifactType();
		try {
			Statement statement1 = DBConn.createStatement();
			ResultSet rs = statement1
					.executeQuery("select * from artifact where artifact_map_id = (select artifact_map_id from artifact where artifact_id = "+artifactId+");");
			while (rs.next()) {
				Artifact artifact = new Artifact();
				artifact.setArtifact_id(rs.getInt(1));
				artifact.setArtifact_name(rs.getString(2));
				artifact.setProject_id(rs.getInt(4));
				artifact.setReview_id(reviewId);
				artifactType = getArtifactType(rs.getInt(5));
				artifact.setArtifact_type(artifactType);
				comments = getArtifactComments(reviewId,
						artifact.getArtifact_id());
				artifact.setComments(comments);
				artifacts.add(artifact);
			}
			for (int i = 0; i < artifacts.size(); i++) {
				for(int j = 0; j<artifacts.get(i).getComments().size();j++){
					artifacts.get(i).getComments().get(j).setUserName(getUserName(artifacts.get(i).getComments().get(j).getUserId()));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return artifacts;		
	}
	
	public ArrayList<Artifact> getArtifactData(int reviewId){
		ArrayList<Comments> comments = null;
		ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
		ArtifactType artifactType = new ArtifactType();
		try {
			Statement statement1 = DBConn.createStatement();
			ResultSet rs = statement1
					.executeQuery("select * from artifact where review_id = "+reviewId+" and is_current = 1;");
			while (rs.next()) {
				Artifact artifact = new Artifact();
				artifact.setArtifact_id(rs.getInt(1));
				artifact.setArtifact_name(rs.getString(2));
				artifact.setArtifactFileName(new File(rs.getString(2)).getName());
				artifact.setProject_id(rs.getInt(4));
				artifact.setReview_id(reviewId);
				artifactType = getArtifactType(rs.getInt(5));
				artifact.setArtifact_type(artifactType);
				comments = getArtifactComments(reviewId,
						artifact.getArtifact_id());
				artifact.setComments(comments);
				artifacts.add(artifact);
			}
			for (int i = 0; i < artifacts.size(); i++) {
				for(int j = 0; j<artifacts.get(i).getComments().size();j++){
					artifacts.get(i).getComments().get(j).setUserName(getUserName(artifacts.get(i).getComments().get(j).getUserId()));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return artifacts;		
	}
	
	private ArtifactType getArtifactType(int artifactTypeId) {
		// TODO Auto-generated method stub
		ResultSet rs;
		ArtifactType artifactType = new ArtifactType();
		try {
			rs = statement
					.executeQuery("select * from artifact_type where artifact_type_id="
							+ artifactTypeId);
			while(rs.next()){
				artifactType.setArtifactType(rs.getString(2));
				artifactType.setArtifactTypeId(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return artifactType;
	}

	private ArrayList<Comments> getArtifactComments(int reviewId, int artifactId) {
		// TODO Auto-generated method stub
		ArrayList<Comments> comments = new ArrayList<Comments>();
		Connection dbCon = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		Statement s;
		try {
			s = dbCon.createStatement();
			ResultSet rs = s
					.executeQuery("select comments.* from comments "
							+ "inner join review on review.review_id = comments.review_id "
							+ "inner join artifact on artifact.artifact_id = comments.artifact_id "
							+ "where review.review_id = " + reviewId
							+ " and artifact.artifact_id = " + artifactId);
			while (rs.next()) {
				Comments comment = new Comments();
				comment.setArtifactId(rs.getInt(5));
				comment.setCommentsId(rs.getInt(1));
				comment.setCommentValue(rs.getString(2));
				comment.setReviewId(rs.getInt(3));
				int userId = rs.getInt(4);
				comment.setUserId(userId);
				comments.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}
	
	private String getUserName(int userId){
		ResultSet rs;
		try {
			rs = statement
					.executeQuery("select user_name from user where user_id="
							+ userId);
			return rs.next() ? rs.getString("user_name"): null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
