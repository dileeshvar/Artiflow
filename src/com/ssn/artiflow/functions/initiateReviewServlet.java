package com.ssn.artiflow.functions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ssn.artiflow.controller.ArtifactManager;
import com.ssn.artiflow.controller.ReviewManager;
import com.ssn.artiflow.controller.UserManager;
import com.ssn.artiflow.models.Artifact;
import com.ssn.artiflow.models.Review;
import com.ssn.artiflow.models.Reviewer;
import com.ssn.artiflow.models.User;

/**
 * Servlet implementation class initiateReviewServlet
 */
public class initiateReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String dbName;
	private String SQLServerIP;

	/**
	 * Default constructor.
	 */
	public initiateReviewServlet() {
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
		String fileName = "";
		String fullFilePath = "";
		boolean valid = false;
		try {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iterator = upload.getItemIterator(request);
			review.setReview_id(reviewMgr.getReviewId());
			while (iterator.hasNext()) {
				Reviewer reviewer = new Reviewer();
				Artifact artifact = new Artifact();
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
							reviewer.setReviewer_id(userMgr.getReviewerId());
							reviewer.setUser_id(userMgr.getUserId(s));
							reviewer.setIs_optional(false);
							reviewer.setReview_id(reviewMgr.getReviewId());
							reviewers.add(reviewer);
						}
						else if(item.getFieldName().equals("optReviewers")){
							if(s.equals(null) || s.equals("")) {
								continue;
							} else {
								int alreadyExistingInDB = userMgr.getReviewerId();
								int finalReviewerId = alreadyExistingInDB + reviewers.size();
								reviewer.setReviewer_id(finalReviewerId);
								reviewer.setUser_id(userMgr.getUserId(s));
								reviewer.setIs_optional(true);
								reviewer.setReview_id(reviewMgr.getReviewId());
								reviewers.add(reviewer);
							}
						}
						else if(item.getFieldName().equals("artifactType")) {
							artifact.setArtifact_id(artMgr.getArtifactId());
							artifact.setArtifact_name(fullFilePath);
							artifact.setArtifact_type(artMgr.getArtifactType(s));
							artifact.setProject_id(reviewMgr.getProjectId(projectName));
							artifact.setReview_id(reviewMgr.getReviewId());
							artifacts.add(artifact);
						}
					} else {
						break;
					}
				} else {
					fileName = item.getName();
					System.out.println("Got an uploaded file: "
							+ item.getFieldName() + ", name = "
							+ fileName);
					if(fileName.equals(null) || fileName.equals("")) {
						valid = false;
					} 
					if(valid) {
						int len;
						File dir = new File("UploadedFiles");
						if(!dir.exists())
							dir.mkdir();
						fullFilePath = dir.getAbsolutePath()+"\\"+ fileName;
						FileOutputStream fOut = new FileOutputStream(fullFilePath);
						BufferedOutputStream buffOut = new BufferedOutputStream(fOut);
						byte[] buffer = new byte[8192];
			            while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
			              buffOut.write(buffer, 0, len);
			              buffOut.flush();
			            }
			            buffOut.close();
					} else {
						break;
					}
				}
			}
			if(!valid) {
				request.setAttribute("Error", "Needed Attributes not entered");
				RequestDispatcher rd = request.getRequestDispatcher("/initDialogForm.jsp");
				rd.forward(request, response);		
			} else {
				review.setReviewers(reviewers);
				review.setArtifacts(artifacts);
				review.setAuthor_id(currentUser.getUserId());
				reviewMgr.updateReview(review);
				RequestDispatcher rd = request.getRequestDispatcher("/initDialogForm.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}