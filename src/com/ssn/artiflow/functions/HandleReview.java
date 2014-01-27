package com.ssn.artiflow.functions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssn.artiflow.controller.ReviewManager;
import com.ssn.artiflow.models.Review;
import com.ssn.artiflow.models.User;

/**
 * Servlet implementation class HandleReview
 */
public class HandleReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String fileSavePath;
	private String dbName;
	private String SQLServerIP;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleReview() {
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
		SQLServerIP = "localhost";
		dbName = "artiflow";
		Review review = new Review();
		HttpSession session = request.getSession();
		String comments = request.getParameter("comments");
		User user = (User) session.getAttribute("User");
		ReviewManager manager = new ReviewManager(SQLServerIP, dbName);
		if(comments!=null && !comments.trim().equals("")){
			review = manager.updateComments(user.getUserId(), comments);
		}
		else{
			review = manager.getReview(user.getUserId());
		}
		if(review.getArtifacts().size()>0){
			request.setAttribute("comments", review.getArtifacts().get(0).getComments());
			request.setAttribute("artifacts", review.getArtifacts().get(0).getArtifact_name());
		}
		request.getRequestDispatcher("/handleReview.jsp").forward(request, response);		
		
	}

}
