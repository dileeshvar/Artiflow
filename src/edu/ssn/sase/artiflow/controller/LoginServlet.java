package edu.ssn.sase.artiflow.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ssn.sase.artiflow.UserValidator.ValidateUser;
import edu.ssn.sase.artiflow.UserValidator.ValidatorInterface;
import edu.ssn.sase.artiflow.functions.ReviewManager;
import edu.ssn.sase.artiflow.functions.UserManager;
import edu.ssn.sase.artiflow.models.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		UserManager userMgr = new UserManager("localhost", "artiflow");
		ReviewManager reviewMgr = new ReviewManager("localhost", "artiflow");
		User user = (User) session.getAttribute("User");
		ValidatorInterface validator = new ValidateUser();
		if(user == null) {
			user = validator.performValidation(userName, password, userMgr);
			session.setAttribute("User", user);
		}
		
		// Check if user is valid
		if(user != null) {
			request.setAttribute("author", validator.getReviewInitiatedByUser(reviewMgr, user));
			request.setAttribute("reviewer", validator.getReviewToBeReviewedByUser(reviewMgr, user));
			RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
			rd.forward(request, response);
		} else {
			request.setAttribute("Flag", "Error");
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
	}
}
