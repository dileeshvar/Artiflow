package edu.ssn.sase.artiflow.InitiateReview;

import java.sql.SQLException;

import edu.ssn.sase.artiflow.functions.ReviewManager;
import edu.ssn.sase.artiflow.models.Review;
import edu.ssn.sase.artiflow.taskmanager.InitiateReviewInterface;

public class InitiateReviewHandler implements InitiateReviewInterface{
	public void initiateReview(Review review, ReviewManager mgr) {
		try {
			mgr.updateReview(review);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
