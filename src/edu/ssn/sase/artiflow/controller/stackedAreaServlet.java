package edu.ssn.sase.artiflow.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class stackedAreaServlet
 */
public class stackedAreaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public stackedAreaServlet() {
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
			request.setAttribute("testData",jSONBuilder.stackedAreaChartJson(1, null, null));
			request.setAttribute("yValue", 1);
			request.getRequestDispatcher("/stackedAreaChart.jsp").forward(request, response);
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
			int yValue = Integer.parseInt(request.getParameter("left"));
			ChartDataBuilder jSONBuilder = new ChartDataBuilder("localhost", "artiflow");
			request.setAttribute("testData",jSONBuilder.stackedAreaChartJson(yValue, null, null));
			request.setAttribute("yValue", yValue);
			request.getRequestDispatcher("/stackedAreaChart.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
