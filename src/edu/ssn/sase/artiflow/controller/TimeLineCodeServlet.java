package edu.ssn.sase.artiflow.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
		if(!handleParamRequest(request,response)){
			doPost(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			TimeLineXMLBuilder xmlBuilder = new TimeLineXMLBuilder();
			String resource = getServletContext().getRealPath("TimelineXMLSource");
			File codeXML = new File(resource+"/EventCode.xml");
			String[] results = request.getParameterValues("type");
			ArrayList<Integer> artifactKey = new ArrayList<Integer>();
			for (int i = 0;results !=null && i < results.length; i++) {
			    artifactKey.add(Integer.parseInt(results[i]));
			}
			xmlBuilder.buildTimelineXML(codeXML, request.getRequestURL().toString(), artifactKey);
			request.setAttribute("codeXMLPath", codeXML.getAbsolutePath());
			request.setAttribute("artifactType", xmlBuilder.getArtifactTypes());
			request.getRequestDispatcher("/timeLine.jsp").forward(request, response);
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
