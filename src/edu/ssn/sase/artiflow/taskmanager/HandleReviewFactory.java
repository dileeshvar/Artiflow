package edu.ssn.sase.artiflow.taskmanager;

import edu.ssn.sase.artiflow.handleReview.HandleReviewHandler;

public class HandleReviewFactory {
	public static HandleReviewInterface getHandleReviewHandler() {
		HandleReviewInterface handleReviewInterface = null;
		handleReviewInterface = new HandleReviewHandler();
		return handleReviewInterface;
	}
}
