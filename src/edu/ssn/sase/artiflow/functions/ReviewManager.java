package edu.ssn.sase.artiflow.functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.ssn.sase.artiflow.models.Artifact;
import edu.ssn.sase.artiflow.models.Comments;
import edu.ssn.sase.artiflow.models.Review;
import edu.ssn.sase.artiflow.models.Reviewer;
import edu.ssn.sase.artiflow.utils.ConnectDB;

public class ReviewManager {
	private String SQLServerIP, databaseName;
	
	public ReviewManager(String sqlServerIP, String databaseName) {
		super();
		SQLServerIP = sqlServerIP;
		this.databaseName = databaseName;
	}

	public int getProjectId(String project_name) throws SQLException {
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		PreparedStatement statement = DBConn.prepareStatement("select * from project where project_name=?");
		statement.setString(1, project_name);
		ResultSet rs = statement.executeQuery();
		int projId = 0;
		while(rs.next()) {
			projId = rs.getInt(1);
		}
		DBConn.close();
		return projId;
	}
	
	public int getReviewId() throws SQLException {
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		ResultSet rs = statement.executeQuery("select count(*) from review");
		int nextId = 0;
		while(rs.next()) {
			int maxId = rs.getInt(1);
			nextId = ++maxId;
		}
		DBConn.close();
		return nextId;
	}

	public void updateReview(Review review) throws SQLException {
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		PreparedStatement prep;
		prep = DBConn.prepareStatement("insert into review (review_id,story_name,objective,project_id,author_id,date_created) values (?,?,?,?,?,?)");
		prep.setInt(1, review.getReview_id());
		prep.setString(2, review.getStoryName());
		prep.setString(3, review.getObjective());
		prep.setInt(4, review.getProject_id());
		prep.setInt(5, 1);
		
		long timeNow = Calendar.getInstance().getTimeInMillis();
		Timestamp ts = new Timestamp(timeNow);
		prep.setTimestamp(6, ts);		
		
		prep.executeUpdate();
		
		for(Artifact arti : review.getArtifacts()) {
			prep = DBConn.prepareStatement("insert into artifact (artifact_id,artifact_name,review_id,Project_id,artifact_type_id, date_created) values (?,?,?,?,?,?)");
			prep.setInt(1, arti.getArtifact_id());
			prep.setString(2, arti.getArtifact_name());
			prep.setInt(3, arti.getReview_id());
			prep.setInt(4, arti.getProject_id());
			prep.setInt(5, arti.getArtifact_type().getArtifactTypeId());
			prep.setTimestamp(6, ts);
			prep.executeUpdate();
		}
		for(Reviewer rev1 : review.getReviewers()) {
			prep = DBConn.prepareStatement("insert into reviewers (reviewers_id,review_id,is_optional,user_id) values (?,?,?,?)");
			prep.setInt(1, rev1.getReviewer_id());
			prep.setInt(2, rev1.getReview_id());
			prep.setBoolean(3, rev1.is_optional());
			prep.setInt(4, rev1.getUser_id());
			prep.executeUpdate();
		}
	}
	
	public Review getReview(int userId){
		Connection dbCon = null;
		Review review =  null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		Statement s;
		try {
			s = dbCon.createStatement();
			ResultSet rs = s.executeQuery("select review.* from review"
					+ " inner join reviewers on reviewers.review_id = review.review_id where reviewers.user_id = "+userId+" order by review.review_id desc;");
			if(rs.next())
				review = updateReviewObject(rs,userId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return review;

	}

	private Review updateReviewObject(ResultSet rs, int userId) {
		// TODO Auto-generated method stub
		Review review = new Review();
		ArrayList<Reviewer> reviewers = new ArrayList<Reviewer>();
		ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
		Reviewer reviewer = null;
		Artifact artifact = null;
		try {
			review.setAuthor_id(rs.getInt(5));
			review.setObjective(rs.getString(3));
			review.setProject_id(rs.getInt(4));
			review.setReview_id(rs.getInt(1));
			review.setStatus_id(rs.getInt(6));
			review.setStoryName(rs.getString(2));
			reviewer = getReviewerData(userId);
			reviewers.add(reviewer);
			review.setReviewers(reviewers);
			artifact = getArtifact(review.getReview_id());
			artifacts.add(artifact);
			review.setArtifacts(artifacts);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return review;
	}

	private Artifact getArtifact(int reviewId) {
		// TODO Auto-generated method stub
		Artifact artifact = new Artifact();
		ArrayList<Comments> comments = null;
		Connection dbCon = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		Statement s;
		try {
			s = dbCon.createStatement();
			ResultSet rs = s.executeQuery("SELECT artifact.* FROM artifact "
					+ "inner join review on artifact.review_id = review.review_id where review.review_id = "+reviewId);
			if(rs.next()){
				artifact.setArtifact_id(rs.getInt(1));
				artifact.setArtifact_name(rs.getString(2));
				artifact.setProject_id(rs.getInt(4));
				artifact.setReview_id(reviewId);
				comments = getArtifactComments(reviewId, artifact.getArtifact_id());
				artifact.setComments(comments);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return artifact;
	}

	private ArrayList<Comments> getArtifactComments(int reviewId,
			int artifactId) {
		// TODO Auto-generated method stub
		ArrayList<Comments> comments = new ArrayList<Comments>();
		Connection dbCon = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		Statement s;
		try {
			s = dbCon.createStatement();
			ResultSet rs = s.executeQuery("select comments.* from comments "
					+ "inner join review on review.review_id = comments.review_id "
					+ "inner join artifact on artifact.artifact_id = comments.artifact_id "
					+ "where review.review_id = "+reviewId+" and artifact.artifact_id = "+artifactId);
			while(rs.next()){
				Comments comment = new Comments();
				comment.setArtifactVersionId(rs.getInt(5));
				comment.setCommentsId(rs.getInt(1));
				comment.setCommentValue(rs.getString(2));
				comment.setReviewId(rs.getInt(3));
				comment.setUserId(rs.getInt(4));
				comments.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}

	public Review updateComments(int userId, String comment, boolean sigOff){
		Review review = getReview(userId);
		Connection dbCon = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		try {
			PreparedStatement preparedStatement = dbCon.prepareStatement("INSERT INTO comments (comments, review_id, user_id, artifact_id) VALUES (?,?,?,?)");
			preparedStatement.setString(1,comment);
			preparedStatement.setInt(2, review.getReview_id());
			preparedStatement.setInt(3, userId);
			preparedStatement.setInt(4, review.getArtifacts().get(0).getArtifact_id());
			preparedStatement.execute();
			if(sigOff)
				updateReview(review.getReview_id());
			review.getArtifacts().get(0).setComments(getArtifactComments(review.getReview_id(), review.getArtifacts().get(0).getArtifact_id()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return review;
	}

	private void updateReview(int reviewID){
		Connection dbCon = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		try {
			System.out.println(reviewID);
			PreparedStatement preparedStatement = dbCon.prepareStatement("update review set end_date = ? where review_id = ?");
			preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			System.out.println(new Timestamp(System.currentTimeMillis()));
			preparedStatement.setInt(2,reviewID);
			int i = preparedStatement.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private Reviewer getReviewerData(int userId) {
		// TODO Auto-generated method stub
		Reviewer reviewer = new Reviewer();
		Connection dbCon = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		Statement s;
		try {
			s = dbCon.createStatement();
			ResultSet rs = s.executeQuery("select reviewers.is_optional, reviewers.review_id, user.user_name from reviewers "
					+ " inner join user on reviewers.user_id = user.user_id where reviewers.user_id = "+userId);
			if(rs.next()){
				reviewer.setIs_optional(rs.getBoolean(1));
				reviewer.setReviewerName(rs.getString(3));
				reviewer.setReview_id(rs.getInt(2));
				reviewer.setUser_id(userId);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reviewer;
	}
	
	public List<String> getUserMailIds(Review review) {
		Connection dbCon = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		List<String> userEmail = new ArrayList<String>();
		List<Integer> userids = getUserIds(review);
		for(int id : userids) {
			PreparedStatement preparedStatement;
			try {
				preparedStatement = dbCon.prepareStatement("select email_id from user where user_id = ?");
				preparedStatement.setInt(1, id);
				ResultSet res = preparedStatement.executeQuery();
				while(res.next()) {
					userEmail.add(res.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return userEmail;
	}

	private List<Integer> getUserIds(Review review) {
		List<Integer> users = new ArrayList<Integer>();
		Connection dbCon = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		try {
			System.out.println(review.getReview_id());
			users.add(review.getAuthor_id());
			PreparedStatement preparedStatement = dbCon.prepareStatement("select user_id from reviewers where review_id = ?");
			preparedStatement.setInt(1, review.getReview_id());
			ResultSet res = preparedStatement.executeQuery();
			while(res.next()) {
				users.add(res.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public String getProjectName(int project_id) throws SQLException {
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		PreparedStatement statement = DBConn.prepareStatement("select * from project where project_id=?");
		statement.setInt(1, project_id);
		ResultSet rs = statement.executeQuery();
		String projName = "";
		while(rs.next()) {
			projName = rs.getString(2);
		}
		DBConn.close();
		return projName;
	}
}
