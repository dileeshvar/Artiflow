package edu.ssn.sase.artiflow.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import edu.ssn.sase.artiflow.UserValidator.ValidateUser;
import edu.ssn.sase.artiflow.UserValidator.ValidatorInterface;
import edu.ssn.sase.artiflow.functions.ReviewManager;
import edu.ssn.sase.artiflow.functions.UserManager;
import edu.ssn.sase.artiflow.models.User;

/**
 * Servlet Filter implementation class AuthenticationValidator
 */
@WebFilter("*")
public class AuthenticationValidator implements Filter {

	String previousPage = "";

	/**
	 * Default constructor.
	 */
	public AuthenticationValidator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String requestedURI = req.getRequestURI();
		previousPage = req.getHeader("Referer");
		User user = (User) session.getAttribute("User");
		String path = StringUtils.removeStart(requestedURI,
				req.getContextPath());
		if (path.equals("/") || path.equals("/login.jsp")
				|| path.equals("/LoginServlet")) {
			if (user != null) {
				ValidatorInterface validator = new ValidateUser();
				ReviewManager reviewMgr = new ReviewManager("localhost",
						"artiflow");
				request.setAttribute("author",
						validator.getReviewInitiatedByUser(reviewMgr, user));
				request.setAttribute("reviewer",
						validator.getReviewToBeReviewedByUser(reviewMgr, user));
				RequestDispatcher rd = request
						.getRequestDispatcher("/home.jsp");
				rd.forward(request, response);
			} else {
				chain.doFilter(request, response);
			}
		} else if ((!(path.startsWith("/style") || path.startsWith("/script")))) {
			if (user == null) {
				req.setAttribute("Flag", "Error");
				RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
		if (null != previousPage
				&& previousPage.endsWith("InitiateReviewScreenServlet")) {
			System.out.println(req.getRequestURI());
			if ((!(req.getRequestURI().endsWith("InitiateReviewScreenServlet") || req
					.getRequestURI().endsWith("UploadServlet")))
					&& (!(req.getRequestURI().endsWith(".js") || req
							.getRequestURI().endsWith(".css")))) {
				if ((session.getAttribute("Upload-File") != null)
						&& (!((String) session.getAttribute("Upload-File"))
								.equals(""))) {
					deleteUninitiatedReviewFiles(req);
				}
			}
		}
	}

	private void deleteUninitiatedReviewFiles(HttpServletRequest request) {
		String reviewFolderName = "";
		ReviewManager revMgr = new ReviewManager("localhost", "artiflow");
		try {
			int nextReviewId = revMgr.getReviewId();
			reviewFolderName = "ReviewFolder" + nextReviewId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String resource = (String) request.getSession().getAttribute(
				"UploadFilePath");
		if (resource != null) {
			resource = resource + "/" + reviewFolderName;
			File dir = new File(resource);
			for (File fileInFolder : dir.listFiles()) {
				fileInFolder.delete();
			}
			HttpSession session = request.getSession();
			session.setAttribute("Upload-File", "");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
