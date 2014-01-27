package com.ssn.artiflow.functions;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssn.artiflow.controller.UserManager;
import com.ssn.artiflow.models.User;

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
		User user = null;
		
		try {
			user = userMgr.checkLogin(userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(user != null) {
			session.setAttribute("User", user);
			if(user.getUserId()==1){
				RequestDispatcher rd = request.getRequestDispatcher("/initDialogForm.jsp");
				rd.forward(request, response);
			}				
			else if(user.getUserId()==2)
				response.sendRedirect("HandleReview");
		} else {
			request.setAttribute("Flag", "Error");
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
	}
}
