package edu.ssn.sase.artiflow.taskmanager;

import edu.ssn.sase.artiflow.InitiateReview.InitiateReviewHandler;



public class InitiateReviewFactory {
	public static InitiateReviewInterface getInitiateReviewHandler() {
		InitiateReviewInterface review = null;
		review = new InitiateReviewHandler();
		return review;
	}
}
