package com.ssn.artiflow.functions;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TimeLineCodeServlet
 */
public class TimeLineCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeLineCodeServlet() {
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
		if(!handleParamRequest(request,response)){
			TimeLineXMLBuilder xmlBuilder = new TimeLineXMLBuilder();
			String resource = getServletContext().getRealPath("TimelineXMLSource");
			File codeXML = new File(resource+"/EventCode.xml");
			xmlBuilder.buildTimelineXML(codeXML, request.getRequestURL().toString());
			request.setAttribute("codeXMLPath", codeXML.getAbsolutePath());
			request.getRequestDispatcher("/timeLine.jsp").forward(request, response);
		}
	}

	private boolean handleParamRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Enumeration<String> parameterNames = request.getParameterNames();
		String reviewID = null;
		String artifactID =  null;
		while(parameterNames.hasMoreElements()){
			artifactID = request.getParameterValues(parameterNames.nextElement())[0];
			reviewID = request.getParameterValues(parameterNames.nextElement())[0];
		}
		if(reviewID==null || artifactID==null)
			return false;
		else{
			TimeLineXMLBuilder timelineBuilder = new TimeLineXMLBuilder();
			timelineBuilder.artifactComments(reviewID, artifactID);
			request.setAttribute("artifactName", timelineBuilder.artifactName(artifactID));
			request.setAttribute("users", timelineBuilder.getUsers());
			request.setAttribute("comments", timelineBuilder.getComments());
			request.getRequestDispatcher("/comments.jsp").forward(request, response);
		}
		return true;
	}

}
