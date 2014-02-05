package edu.ssn.sase.artiflow.UserValidator;

import java.util.List;

import edu.ssn.sase.artiflow.functions.ReviewManager;
import edu.ssn.sase.artiflow.functions.UserManager;
import edu.ssn.sase.artiflow.models.Review;
import edu.ssn.sase.artiflow.models.User;

public interface ValidatorInterface {
	public User performValidation(String userName, String password, UserManager mgr);
	public List<Review> getReviewInitiatedByUser(ReviewManager reviewMgr, User user);
	public List<Review> getReviewToBeReviewedByUser(ReviewManager reviewMgr, User user);
}
