package edu.ssn.sase.artiflow.taskmanager;

import java.sql.SQLException;

import edu.ssn.sase.artiflow.functions.ReviewManager;
import edu.ssn.sase.artiflow.models.Review;

public interface InitiateReviewInterface {
	public void initiateReview(Review review, ReviewManager mgr) throws SQLException;
}
