package edu.ssn.sase.artiflow.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ssn.sase.artiflow.functions.ArtiflowManager;
import edu.ssn.sase.artiflow.models.User;

/**
 * Servlet implementation class InitiateReviewScreenServlet
 */
@WebServlet("/InitiateReviewScreenServlet")
public class InitiateReviewScreenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitiateReviewScreenServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("User");
			ArtiflowManager artiMgr = new ArtiflowManager("localhost",
					"artiflow");
			List<Integer> projectIds = artiMgr.getProjectId(user.getUserId());

			List<String> projectList = artiMgr.getProjects(projectIds);
			request.getSession().setAttribute("Project_List", projectList);

			List<String> reviewersList = artiMgr.getUsersOfProject(projectIds);
			request.getSession().setAttribute("Reviewers", reviewersList);

			List<String> artiType = artiMgr.getArtifactType();
			request.getSession().setAttribute("Artifact_Type", artiType);
			request.setAttribute("Status", request.getAttribute("Status"));
			RequestDispatcher rd = request.getRequestDispatcher("/initDialogForm.jsp");
			rd.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
