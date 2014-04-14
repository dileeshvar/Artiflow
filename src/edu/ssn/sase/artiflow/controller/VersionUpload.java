package edu.ssn.sase.artiflow.controller;

import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ssn.sase.artiflow.functions.ArtifactManager;
import edu.ssn.sase.artiflow.models.Artifact;

/**
 * Servlet implementation class VersionUpload
 */
@WebServlet("/VersionUpload")
public class VersionUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VersionUpload() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String filePath = (String) session.getAttribute("Upload-File");
		String artifactId = request.getParameter("artifactId");
		System.out.println(artifactId);
		artifactId = session.getAttribute("artifactId").toString();
		System.out.println(artifactId);
		ArtifactManager mgr = new ArtifactManager("localhost", "artiflow");
		Artifact oldArtifact = mgr.getArtifact(Integer.valueOf(artifactId));
		Artifact newArtifact = new Artifact();
		try {
			int nextArtIdFromDB = mgr.getArtifactId();
			newArtifact.setArtifact_id(nextArtIdFromDB);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		newArtifact.setArtifact_map_id(oldArtifact.getArtifact_map_id());
		newArtifact.setArtifact_name(filePath);
		newArtifact.setArtifact_type(oldArtifact.getArtifact_type());
		newArtifact.setCurrent(true);
		newArtifact.setProject_id(oldArtifact.getProject_id());
		newArtifact.setReview_id(oldArtifact.getReview_id());
		mgr.updateOldArtifact(oldArtifact);
		mgr.uploadNewArtifact(newArtifact);
		session.setAttribute("Upload-File", null);
		session.setAttribute("artifactId" , null);
		RequestDispatcher rd = request.getRequestDispatcher("/LoginServlet");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
