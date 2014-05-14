package edu.ssn.sase.artiflow.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LineBarServlet
 */
public class LineBarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LineBarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			ChartDataBuilder jSONBuilder = new ChartDataBuilder("localhost", "artiflow");
			request.setAttribute("testData",jSONBuilder.LineBarChartJson(1, 2));
			request.setAttribute("lValue", 1);
			request.setAttribute("rValue", 2);
			request.getRequestDispatcher("/linePlusBarWithFocusChart.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			int leftOpt = Integer.parseInt(request.getParameter("left"));
			int rightOpt = Integer.parseInt(request.getParameter("right"));
			ChartDataBuilder jSONBuilder = new ChartDataBuilder("localhost", "artiflow");
			request.setAttribute("testData",jSONBuilder.LineBarChartJson(leftOpt, rightOpt));
			request.setAttribute("lValue", leftOpt);
			request.setAttribute("rValue", rightOpt);
			request.getRequestDispatcher("/linePlusBarWithFocusChart.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
