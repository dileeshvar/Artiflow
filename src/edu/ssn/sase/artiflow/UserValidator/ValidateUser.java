package edu.ssn.sase.artiflow.UserValidator;

import java.sql.SQLException;
import java.util.List;

import edu.ssn.sase.artiflow.functions.ReviewManager;
import edu.ssn.sase.artiflow.functions.UserManager;
import edu.ssn.sase.artiflow.models.Review;
import edu.ssn.sase.artiflow.models.Reviewer;
import edu.ssn.sase.artiflow.models.User;

public class ValidateUser implements ValidatorInterface{

	public User performValidation(String userName, String password, UserManager mgr) {
		User user = null;
		try {
			user = mgr.checkLogin(userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<Review> getReviewInitiatedByUser(ReviewManager rev, User user) {
		List<Review> reviews = null;
		try {
			reviews = rev.getReviewInitiatedByUser(user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviews;
	}

	public List<Review> getReviewToBeReviewedByUser(ReviewManager reviewMgr,
			User user) {
		List<Review> reviews = null;
		try {
			reviews = reviewMgr.getReviewToBeReviewedByUser(user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviews;
	}

}
