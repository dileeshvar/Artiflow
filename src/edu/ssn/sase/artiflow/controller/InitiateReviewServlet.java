package edu.ssn.sase.artiflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import edu.ssn.sase.artiflow.InitiateReview.InitiateReviewHandler;
import edu.ssn.sase.artiflow.functions.ArtifactManager;
import edu.ssn.sase.artiflow.functions.ReviewManager;
import edu.ssn.sase.artiflow.functions.UserManager;
import edu.ssn.sase.artiflow.models.Artifact;
import edu.ssn.sase.artiflow.models.Review;
import edu.ssn.sase.artiflow.models.Reviewer;
import edu.ssn.sase.artiflow.models.User;
import edu.ssn.sase.artiflow.taskmanager.InitiateReviewFactory;

/**
 * Servlet implementation class initiateReviewServlet
 */
public class InitiateReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String dbName;
	private String SQLServerIP;

	/**
	 * Default constructor.
	 */
	public InitiateReviewServlet() {
	}

	public void init() {
		dbName = getServletConfig().getInitParameter("dbName");
		SQLServerIP = getServletConfig().getInitParameter("serverIp");
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
		
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("User");
		SQLServerIP = "localhost";
		dbName = "artiflow";

		ReviewManager reviewMgr = new ReviewManager(SQLServerIP, dbName);
		UserManager userMgr = new UserManager(SQLServerIP, dbName);
		ArtifactManager artMgr = new ArtifactManager(SQLServerIP, dbName);
		
		Review review = new Review();
		List<Artifact> artifacts = new ArrayList<Artifact>();
		List<Reviewer> reviewers = new ArrayList<Reviewer>();
		String projectName = "";
		String paths = null;
		boolean valid = false;
		try {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iterator = upload.getItemIterator(request);
			review.setReview_id(reviewMgr.getReviewId());
			while (iterator.hasNext()) {
				Reviewer reviewer = new Reviewer();
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					byte[] buffer = new byte[8192];
		            int len;
		            String s = "";
					while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
						s = new String(buffer);
						s = s.trim();
					}
					if(len == -1 && (s.equals(null) || s.equals("")) && !item.getFieldName().equals("optReviewers")) {
						valid = false;
					} else {
						if(!item.getFieldName().equals("submit"))
							valid = true;
					}
					if(valid) {
						if(item.getFieldName().equals("projectName")) {
							projectName = s;
							review.setProject_id(reviewMgr.getProjectId(s));
						}
						else if(item.getFieldName().equals("storyname"))
							review.setStoryName(s);
						else if(item.getFieldName().equals("objective"))
							review.setObjective(s);
						else if(item.getFieldName().equals("mainReviewers")) {
							int lastReviewerIdFromDB = userMgr.getReviewerId();
							int currentReviewerId = lastReviewerIdFromDB + reviewers.size();
							reviewer.setReviewer_id(currentReviewerId);
							reviewer.setUser_id(userMgr.getUserId(s));
							reviewer.setIs_optional(false);
							reviewer.setReview_id(reviewMgr.getReviewId());
							reviewers.add(reviewer);
						}
						else if(item.getFieldName().equals("optReviewers")){
							if(s.equals(null) || s.equals("")) {
								continue;
							} else {
								int lastReviewerIdFromDB = userMgr.getReviewerId();
								int finalReviewerId = lastReviewerIdFromDB + reviewers.size();
								reviewer.setReviewer_id(finalReviewerId);
								reviewer.setUser_id(userMgr.getUserId(s));
								reviewer.setIs_optional(true);
								reviewer.setReview_id(reviewMgr.getReviewId());
								reviewers.add(reviewer);
							}
						}
						else if(item.getFieldName().equals("artifactType")) {
							paths = (String) request.getSession().getAttribute("Upload-File");
							if(paths == null) {
								valid = false;
								break;
							}
							String[] filePaths = paths.split(";;;");
							int lastArtIdFromDB = artMgr.getArtifactId();
							for(String artiPath : filePaths) {
								Artifact artifact = new Artifact();
								artifact.setArtifact_id(lastArtIdFromDB + artifacts.size());
								artifact.setArtifact_name(artiPath);
								artifact.setArtifact_type(artMgr.getArtifactType(s));
								artifact.setProject_id(reviewMgr.getProjectId(projectName));
								artifact.setReview_id(reviewMgr.getReviewId());
								artifacts.add(artifact);
							}
						}
					} else {
						break;
					}
				}
			}
			if(!valid) {
				request.setAttribute("Status", "Failure");
				response.sendRedirect("InitiateReviewScreenServlet");
			} else {
				review.setReviewers(reviewers);
				review.setArtifacts(artifacts);
				review.setAuthor_id(currentUser.getUserId());
				InitiateReviewHandler init = (InitiateReviewHandler) InitiateReviewFactory.getInitiateReviewHandler();
				init.initiateReview(review, reviewMgr);
				String resource = getServletContext().getRealPath("artiflowConfig");
				String configPath = resource + "/Mail.properties";
				init.sendNotification(review, reviewMgr, configPath);
				request.setAttribute("Status", "Success");
				request.getSession().setAttribute("Upload-File", null);
				response.sendRedirect("InitiateReviewScreenServlet");
			}
		} catch (Exception e) {
			request.setAttribute("Status", "Error");
			response.sendRedirect("InitiateReviewScreenServlet");
		}
	}
}