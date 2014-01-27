package edu.ssn.sase.artiflow.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ssn.sase.artiflow.InitiateReview.InitiateReviewHandler;
import edu.ssn.sase.artiflow.functions.ReviewManager;
import edu.ssn.sase.artiflow.handleReview.HandleReviewHandler;
import edu.ssn.sase.artiflow.handleReview.HandleReviewListOfObjects;
import edu.ssn.sase.artiflow.models.Review;
import edu.ssn.sase.artiflow.models.User;
import edu.ssn.sase.artiflow.taskmanager.HandleReviewFactory;
import edu.ssn.sase.artiflow.taskmanager.InitiateReviewFactory;

/**
 * Servlet implementation class HandleReview
 */
public class HandleReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String dbName;
	private String SQLServerIP;
	private int reviewID = 0;
	private int artifactID =  0;
	private boolean isAuthor = false;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(handleParamRequest(request, response)){
			request.getRequestDispatcher("/handleReview.jsp").forward(request, response);
		}
		else{
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	private boolean handleParamRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String comments = null;
		boolean signOff  = false;
		if(request.getParameter("a_id") != null && request.getParameter("r_id") !=null && request.getParameter("is_a")!=null){
			artifactID = Integer.parseInt(request.getParameter("a_id"));
			reviewID = Integer.parseInt(request.getParameter("r_id"));
			isAuthor = request.getParameter("is_a").equals("0") ? false : true;
			System.out.println("author "+isAuthor+" rev"+reviewID+" auth"+artifactID);
		}
		if(false)
			return false;
		else{
			SQLServerIP = "localhost";
			dbName = "artiflow";
			Review review = new Review();
			System.out.println("context "+request.getRequestURI());
			System.out.println("abs path "+getServletContext().getRealPath("artiflowConfig"));
			HttpSession session = request.getSession();
			comments = request.getParameter("comments");
			signOff= request.getParameter("Sign-off")!=null ? true : false;
			User user = (User) session.getAttribute("User");
			ReviewManager manager = new ReviewManager(SQLServerIP, dbName);
			if(comments!=null && !comments.trim().equals("")){
				review = manager.updateComments(user.getUserId(), comments, signOff, reviewID, artifactID);
				System.out.println("inside");
			}
			HandleReviewHandler handler = (HandleReviewHandler) HandleReviewFactory.getHandleReviewHandler();
			HandleReviewListOfObjects handleReviewListOfObjects = handler.getReviewArtifactData(reviewID, artifactID);
			request.setAttribute("artifacts", handleReviewListOfObjects.getArtifact());
			request.setAttribute("artifactVersion", handleReviewListOfObjects.getArtifactVersion());
			request.setAttribute("comments", handleReviewListOfObjects.getComments());
			request.setAttribute("isAuthor", isAuthor);
		}
		return true;
	}
}
