package edu.ssn.sase.artiflow.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.rmi.runtime.NewThreadAction;

/**
 * Servlet implementation class StackedAreaServlet
 */
public class CumulativeLineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CumulativeLineServlet() {
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
			request.getRequestDispatcher("/cumulativeLineChart.jsp").forward(request, response);
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
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Timestamp start =  null;
			Timestamp end =  null;
			if(request.getParameter("start")!= null){
				Date startDate = dateFormat.parse(request.getParameter("start"));
				Date endDate = dateFormat.parse(request.getParameter("end"));
				start = new Timestamp(startDate.getTime());
				end = new Timestamp(endDate.getTime());
			}
			ChartDataBuilder jSONBuilder = new ChartDataBuilder("localhost", "artiflow");
			request.setAttribute("testData",jSONBuilder.stackedAreaChartJson(yValue, start!=null ? start.toString().substring(0, 19):null, end!=null ? end.toString().substring(0, 19): null));
			request.setAttribute("yValue", yValue);
			request.getRequestDispatcher("/cumulativeLineChart.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
