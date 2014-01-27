package edu.ssn.sase.artiflow.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ssn.sase.artiflow.dal.ReviewDal;
import edu.ssn.sase.artiflow.models.User;

/**
 * Servlet implementation class ReviewManager
 */
public class ReviewManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewManagerServlet() {
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
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("User");
		boolean isAuthor = true;
		if(request.getRequestURL().toString().contains("ReviewManagerReviewer"))
			isAuthor = false;
		ReviewDal reviewDal = new ReviewDal();
		try {
			reviewDal.initiateParams();
			ArrayList<edu.ssn.sase.artiflow.models.Review> reviews = reviewDal.getReviewList(currentUser.getUserId(), isAuthor);
			request.setAttribute("reviews", reviews);
			request.setAttribute("URL", request.getRequestURL().toString().substring(0, request.getRequestURL().toString().lastIndexOf("/")) + "/HandleReview");
			request.setAttribute("isAuthor", isAuthor ? 1 : 0);
			System.out.println("size "+reviews.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("/dashboard.jsp").forward(request, response);	
	}

}
