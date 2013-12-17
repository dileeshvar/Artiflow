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

import edu.ssn.sase.artiflow.dal.ReviewDal;
import edu.ssn.sase.artiflow.models.Artifact;
import edu.ssn.sase.artiflow.models.Comments;
import edu.ssn.sase.artiflow.models.Review;
import edu.ssn.sase.artiflow.models.Reviewer;
import edu.ssn.sase.artiflow.utils.ConnectDB;

public class ReviewManager {
	ReviewDal dal = new ReviewDal();

	public ReviewManager(String sqlServerIP, String databaseName) {
		super();
		try {
			dal.initiateParams(sqlServerIP, databaseName);
		} catch (SQLException e) {
			System.out.println("Failure Happened!!!");
		}
	}

	public int getProjectId(String project_name) throws SQLException {
		ResultSet rs = dal.getProjectFromProjName(project_name);
		int projId = 0;
		while (rs.next()) {
			projId = rs.getInt(1);
		}
		return projId;
	}

	public int getReviewId() throws SQLException {
		ResultSet rs = dal.getReviewCount();
		int nextId = 0;
		while (rs.next()) {
			int maxId = rs.getInt(1);
			nextId = ++maxId;
		}
		return nextId;
	}

	public void updateReview(Review review) throws SQLException {
		long timeNow = Calendar.getInstance().getTimeInMillis();
		Timestamp ts = new Timestamp(timeNow);

		dal.updateReview(review, ts);

		for (Artifact arti : review.getArtifacts()) {
			dal.updateArtifacts(arti, ts);
		}

		for (Reviewer rev1 : review.getReviewers()) {
			dal.updateReviewers(rev1);
		}
	}

	public Review getReview(int userId) {
		Connection dbCon = null;
		Review review = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		Statement s;
		try {
			s = dbCon.createStatement();
			ResultSet rs = s
					.executeQuery("select review.* from review"
							+ " inner join reviewers on reviewers.review_id = review.review_id where reviewers.user_id = "
							+ userId + " order by review.review_id desc;");
			if (rs.next())
				review = updateReviewObject(rs, userId);

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
			ResultSet rs = s
					.executeQuery("SELECT artifact.* FROM artifact "
							+ "inner join review on artifact.review_id = review.review_id where review.review_id = "
							+ reviewId);
			if (rs.next()) {
				artifact.setArtifact_id(rs.getInt(1));
				artifact.setArtifact_name(rs.getString(2));
				artifact.setProject_id(rs.getInt(4));
				artifact.setReview_id(reviewId);
				comments = getArtifactComments(reviewId,
						artifact.getArtifact_id());
				artifact.setComments(comments);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return artifact;
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

	public Review updateComments(int userId, String comment, boolean sigOff) {
		Review review = getReview(userId);
		Connection dbCon = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		try {
			PreparedStatement preparedStatement = dbCon
					.prepareStatement("INSERT INTO comments (comments, review_id, user_id, artifact_id) VALUES (?,?,?,?)");
			preparedStatement.setString(1, comment);
			preparedStatement.setInt(2, review.getReview_id());
			preparedStatement.setInt(3, userId);
			preparedStatement.setInt(4, review.getArtifacts().get(0)
					.getArtifact_id());
			preparedStatement.execute();
			if (sigOff)
				updateReview(review.getReview_id());
			review.getArtifacts()
					.get(0)
					.setComments(
							getArtifactComments(review.getReview_id(), review
									.getArtifacts().get(0).getArtifact_id()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return review;
	}

	private void updateReview(int reviewID) {
		Connection dbCon = null;
		dbCon = ConnectDB.getConnection("localhost", "artiflow");
		try {
			System.out.println(reviewID);
			PreparedStatement preparedStatement = dbCon
					.prepareStatement("update review set end_date = ? where review_id = ?");
			preparedStatement.setTimestamp(1,
					new Timestamp(System.currentTimeMillis()));
			System.out.println(new Timestamp(System.currentTimeMillis()));
			preparedStatement.setInt(2, reviewID);
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
			ResultSet rs = s
					.executeQuery("select reviewers.is_optional, reviewers.review_id, user.user_name from reviewers "
							+ " inner join user on reviewers.user_id = user.user_id where reviewers.user_id = "
							+ userId);
			if (rs.next()) {
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
		for (int id : userids) {
			PreparedStatement preparedStatement;
			try {
				preparedStatement = dbCon
						.prepareStatement("select email_id from user where user_id = ?");
				preparedStatement.setInt(1, id);
				ResultSet res = preparedStatement.executeQuery();
				while (res.next()) {
					userEmail.add(res.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userEmail;
	}

	private List<Integer> getUserIds(Review review) {
		List<Integer> users = new ArrayList<Integer>();
		try {
			users.add(review.getAuthor_id());
			ResultSet res = dal.getReviewerIdFromReview(review);
			while (res.next()) {
				users.add(res.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public String getProjectName(int project_id) throws SQLException {
		ResultSet rs = dal.getProjNameFromProjId(project_id);
		String projName = "";
		while (rs.next()) {
			projName = rs.getString(2);
		}
		return projName;
	}
}
