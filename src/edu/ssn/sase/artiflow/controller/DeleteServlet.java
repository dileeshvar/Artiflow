package edu.ssn.sase.artiflow.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import edu.ssn.sase.artiflow.functions.ReviewManager;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
@MultipartConfig
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		final Part file = request.getPart("File");
		String fileName = getValue(file);

		String reviewFolderName = "";
		ReviewManager revMgr = new ReviewManager("localhost", "artiflow");
		try {
			int nextReviewId = revMgr.getReviewId();
			reviewFolderName = "ReviewFolder" + nextReviewId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String resource = getServletContext().getRealPath("UploadedFiles");
		request.setAttribute("UploadFilePath", resource);
		
		resource = resource + "/" + reviewFolderName;
		File dir = new File(resource);
		for (File fileInFolder : dir.listFiles()) {
			String dirFileName = fileInFolder.getName().split("_")[1];
			if(dirFileName.equals(fileName)) {
				fileInFolder.delete();
			}
		}
		HttpSession session = request.getSession();
		String[] filePath = ((String) session.getAttribute("Upload-File")).split(";;;");
		String updatedFilePath = "";
		for(String files: filePath) {
			if(filePath.length == 1 && files.equals("")) {
				break;
			}
			String fileNameFromSession = getFileNameFromPath(files);
			if(fileName.equals(fileNameFromSession)) {
				continue;
			} else {
				if(updatedFilePath.equals("")) {
					updatedFilePath = files;
				} else {
					updatedFilePath = updatedFilePath + ";;;" + files;
				}
				
			}
		}
		session.setAttribute("Upload-File", updatedFilePath);

	}
	
	private String getFileNameFromPath(String files) {
		return (files.substring(files.lastIndexOf("\\")+1, files.length())).split("_")[1];
	}

	private static String getValue(Part part) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
	    StringBuilder value = new StringBuilder();
	    char[] buffer = new char[1024];
	    for (int length = 0; (length = reader.read(buffer)) > 0;) {
	        value.append(buffer, 0, length);
	    }
	    return value.toString();
	}
}
