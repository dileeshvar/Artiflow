package edu.ssn.sase.artiflow.controller;

import java.io.IOException;

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

import edu.ssn.sase.artiflow.models.User;

/**
 * Servlet Filter implementation class AuthenticationValidator
 */
@WebFilter("*")
public class AuthenticationValidator implements Filter {

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
		System.out.println("Filter caught");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("User");
		String path = StringUtils.removeStart(req.getRequestURI(), req.getContextPath());
		if(path.equals("/") || path.equals("/login.jsp") || path.equals("/LoginServlet")) {
			if (user == null) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).sendRedirect("InitiateReviewScreenServlet");
			}
		} else if(!path.equals("/LoginServlet")) {
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
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
