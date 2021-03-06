package edu.ssn.sase.artiflow.InitiateReview;

import java.sql.SQLException;
import java.util.List;

import edu.ssn.sase.artiflow.MailManager.ArtiflowMailHandler;
import edu.ssn.sase.artiflow.functions.ReviewManager;
import edu.ssn.sase.artiflow.models.Review;
import edu.ssn.sase.artiflow.taskmanager.InitiateReviewInterface;

public class InitiateReviewHandler implements InitiateReviewInterface{
	public void initiateReview(Review review, ReviewManager mgr) throws SQLException{
		review.setStatus_id(1);
		mgr.updateReview(review);
	}

	public void sendNotification(Review review, ReviewManager mgr, String configFilePath) {
		ArtiflowMailHandler hdlr = new ArtiflowMailHandler();
		List<String> mailIds = mgr.getUserMailIds(review);
		String projectName = "";
		try {
			projectName = mgr.getProjectName(review.getProject_id());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hdlr.sendMailNow(mailIds, projectName, configFilePath);
	}
}
